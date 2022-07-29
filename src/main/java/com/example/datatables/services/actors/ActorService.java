package com.example.datatables.services.actors;

import com.example.datatables.models.actors.Actor;
import com.example.datatables.models.actors.ActorComparators;
import com.example.datatables.models.search.*;
import com.example.datatables.repositories.actors.ActorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
        if (request.getSearch() == null || StringUtils.isEmpty(request.getSearch()
                .getValue())) {
            return actor -> true;
        }

        String value = request.getSearch()
                .getValue();

        return actor -> actor.getFirstName()
                .toLowerCase()
                .contains(value)
                || actor.getLastName()
                .toLowerCase()
                .contains(value)
                || actor.getLastUpdate().toString().toLowerCase()
                .contains(value);
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
}
