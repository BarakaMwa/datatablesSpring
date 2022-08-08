package com.example.datatables.models.actors;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.example.datatables.models.search.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public final class ActorComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<Actor>> map = new HashMap<>();

    static {
        map.put(new Key("first_name", Direction.asc), Comparator.comparing(Actor::getFirstName));
        map.put(new Key("first_name", Direction.desc), Comparator.comparing(Actor::getFirstName)
                .reversed());

        map.put(new Key("last_name", Direction.asc), Comparator.comparing(Actor::getLastName));
        map.put(new Key("last_name", Direction.desc), Comparator.comparing(Actor::getLastName)
                .reversed());

        map.put(new Key("last_update", Direction.asc), Comparator.comparing(Actor::getLastUpdate));
        map.put(new Key("last_update", Direction.desc), Comparator.comparing(Actor::getLastUpdate)
                .reversed());
    }

    public static Comparator<Actor> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private ActorComparators() {
    }

}