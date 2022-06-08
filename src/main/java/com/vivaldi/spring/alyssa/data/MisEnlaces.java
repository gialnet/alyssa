package com.vivaldi.spring.alyssa.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "mis-enlaces")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MisEnlaces {

    @Id
    String id;
    String email;
    String description;
    String link;
    String tags;
}
