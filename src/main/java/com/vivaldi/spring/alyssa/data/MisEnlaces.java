package com.vivaldi.spring.alyssa.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "mis-enlaces-v10")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MisEnlaces {

    @Id
    @Field(type = FieldType.Keyword, store = true )
    String Id2;
    String email;
    String description;
    String link;
    String tags;
}
