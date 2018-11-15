package epers.bichomon.dao.mongodb;

import org.jongo.Find;

@FunctionalInterface
public interface FindBlock {
    Find executeWith(Find find);
}
