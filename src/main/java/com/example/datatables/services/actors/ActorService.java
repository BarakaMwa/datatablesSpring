package com.example.datatables.services.actors;

import com.example.datatables.dtos.ActorIdFirstLastDto;
import com.example.datatables.models.actors.Actor;
import com.example.datatables.models.actors.ActorComparators;
import com.example.datatables.models.search.*;
import com.example.datatables.repositories.actors.ActorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ActorService {


    private static final Comparator<Actor> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private final ActorRepository actorRepository;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Page<Actor> getActors(PagingRequest request) {

        try {
            List<Actor> actors = actorRepository.findAll();

            return getPage(actors, request);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new Page<>();

    }

    private Page<Actor> getPage(List<Actor> actors, PagingRequest request) {
        List<Actor> filtered = actors.stream()
                .sorted(sortActors(request))
                .filter(filterActors(request))
                .skip(request.getStart())
                .limit(request.getLength())
                .collect(Collectors.toList());

        long count = actors.stream()
                .filter(filterActors(request))
                .count();

        Page<Actor> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(request.getDraw());

        return page;
    }

    private Predicate<? super Actor> filterActors(PagingRequest request) {
        if (request.getSearch() == null || "".equalsIgnoreCase(request.getSearch().getValue())) {
            return actor -> true;
        }

        String value = request.getSearch()
                .getValue();

        return actor -> actor.getFirstName()
                .toLowerCase()
                .contains(value.toLowerCase())
                || actor.getLastName()
                .toLowerCase()
                .contains(value.toLowerCase());
    }

    private Comparator<? super Actor> sortActors(PagingRequest request) {
        if (request.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = request.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = request.getColumns()
                    .get(columnIndex);

            Comparator<Actor> comparator = ActorComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }

    public PageArray getActorsArray(PagingRequest request) {

        request.setColumns(Stream.of("first_name", "last_name", "last_update")
                .map(Column::new)
                .collect(Collectors.toList()));
        Page<Actor> actorPage = getActors(request);

        PageArray pageArray = new PageArray();
        pageArray.setRecordsFiltered(actorPage.getRecordsFiltered());
        pageArray.setRecordsTotal(actorPage.getRecordsTotal());
        pageArray.setDraw(actorPage.getDraw());
        pageArray.setData(actorPage.getData()
                .stream()
                .map(this::toStringList)
                .collect(Collectors.toList()));
        return pageArray;

    }

    private List<String> toStringList(Actor actor) {
        return Arrays.asList(actor.getFirstName(), actor.getLastName(), sdf.format(actor.getLastUpdate()));
    }

    public Map<String, Object> getActorsSelectList(HttpServletRequest request) {

        HashMap<String, Object> response = new HashMap<>();
        List<Actor> actors;

        if (Objects.nonNull(request.getParameter("q"))) {
            String searchValue = request.getParameter("q");
  /*          actors = actorRepository.findTop100ByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchValue, searchValue);
            actors = actorRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchValue, searchValue, PageRequest.of(0, 100));*/
            List<ActorIdFirstLastDto> actorDtos = actorRepository.getByFirstOrLastAndId(searchValue, searchValue, PageRequest.of(0, 100));
            actors = mapDtoToActorModel(actorDtos);

        }else{
            actors = actorRepository.findTop100ByActorIdIsNotNull();
        }

        actors = actors.stream()
                .limit(10)
                .collect(Collectors.toList());

        response.put("items", actors);
        response.put("total_count", actors.size());
        response.put("incomplete_results", true);

        return response;

    }

    private List<Actor> mapDtoToActorModel(List<ActorIdFirstLastDto> actorDtos) {
        List<Actor> actors = new ArrayList<>();
        for (ActorIdFirstLastDto item : actorDtos) {
            Actor actor = new Actor();

            actor.setActorId(item.getId());
            actor.setFirstName(item.getFirstName());
            actor.setLastName(item.getLastName());

            actors.add(actor);
        }


        return actors;
    }

}
