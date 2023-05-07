package pl.kartven.javaproapp.data;

import java.util.ArrayList;
import java.util.List;

import pl.kartven.javaproapp.data.model.AuthApi;
import pl.kartven.javaproapp.data.model.RangeApiDetails;
import pl.kartven.javaproapp.data.model.SlideApi;

public class Mock {
    private Mock() {
    }

    public static AuthApi authApi = new AuthApi(
            "nicknameMock",
            "bearerMock",
            "refreshMock"
    );

    public static List<RangeApiDetails> lectureRangeApiDetailsList = new ArrayList<>();

    static {
        for (long i = 1; i <= 10; i++) {
            lectureRangeApiDetailsList.add(new RangeApiDetails(
                    i, "Wykład " + i, "Opis " + i
            ));
        }
    }

    public static List<RangeApiDetails> laboratoryRangeApiDetailsList = new ArrayList<>();

    static {
        for (long i = 1; i <= 5; i++) {
            laboratoryRangeApiDetailsList.add(new RangeApiDetails(
                    i, "Wykład " + i, "Opis " + i
            ));
        }
    }

    public static List<SlideApi> lectureSlideApiList = new ArrayList<>();

    static {
        for (long i = 1; i <= 5; i++) {
            lectureSlideApiList.add(new SlideApi(i, new byte[0]));
        }
    }
}
