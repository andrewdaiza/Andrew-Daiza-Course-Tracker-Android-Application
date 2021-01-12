package edu.wgu.c196.andrewdaiza.adapters;

import edu.wgu.c196.andrewdaiza.database.entities.findId;

public interface ClickListener<T extends findId> {
    void onItemClick(T entity);
}
