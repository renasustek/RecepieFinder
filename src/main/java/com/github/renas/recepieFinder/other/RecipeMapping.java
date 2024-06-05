package com.github.renas.recepieFinder.other;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;
import java.util.UUID;

@Document(indexName = "sample")
public class RecipeMapping {
    @Id
    private UUID id;

    @Field
    private String name;

    @Field
    private String description;

    @Field
    private List<String> ingredients;

    @Field
    private List<String> steps;
}
