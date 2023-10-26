package cost.admin.app.model.input;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class SpendDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "summary", required = true)
    private String summary;

    @JsonProperty(value = "category", required = true)
    private String category;

    @JsonProperty(value = "paid", required = true)
    @JsonFormat(pattern="yyyy-MM-ddTHH:mm")
    private String paid;

    @JsonProperty(value = "sum", required = true)
    private double sum;

    @JsonProperty(value = "currency", required = true)
    private String currency;

}
