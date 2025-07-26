package shop.server.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import shop.server.model.enums.MasterDataType;

@Getter
@Setter
@Entity
@Table(name = "master_data")
public class MasterDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type;

    @Nationalized
    @Lob
    @Column(name = "json_data")
    private String jsonData;

}