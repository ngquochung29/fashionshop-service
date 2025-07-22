package shop.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 22/7/2025,
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto {
    private int pageCurrent;
    private int size;
    private long totalRecords;
    private int totalPages;
    private List<?> data;
}
