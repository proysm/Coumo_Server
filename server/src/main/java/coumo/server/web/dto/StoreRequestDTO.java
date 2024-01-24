package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class StoreRequestDTO {

    //가게 수정(기본 정보)
    @Getter
    public static class updateBasicDTO{
        public String name;
        public List<time> time;
        public String telePhone;
        public String category;
        public String location;
        public String longitude;
        public String latitude;
    }

    @Getter
    public static class time{
        String day;
        String startTime;
        String endTime;
    }

    @Getter
    public static class MenuDetail{
        String name;
        String description;
    }
}
