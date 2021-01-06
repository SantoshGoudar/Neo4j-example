package com.example.demoNeo4j.model;

import java.util.List;
import lombok.Data;

@Data
public class BulkFriendRequest {
    long sourceId;
    List<Long> targetIds;
}
