package com.vivaldi.spring.alyssa.data;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BrowsePages {

    Integer totalPages;
    Integer numberOfRecords;
    @Builder.Default
    Integer currentPage = 1;
}
