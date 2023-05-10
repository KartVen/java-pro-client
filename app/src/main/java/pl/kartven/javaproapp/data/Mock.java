package pl.kartven.javaproapp.data;

import java.util.ArrayList;
import java.util.List;

import pl.kartven.javaproapp.data.model.AuthApi;
import pl.kartven.javaproapp.data.model.SingleRangeApiDetails;
import pl.kartven.javaproapp.data.model.SlideApi;

public class Mock {
    private Mock() {
    }

    public static AuthApi authApi = new AuthApi(
            "nicknameMock",
            "bearerMock",
            "refreshMock"
    );

    public static List<SingleRangeApiDetails> lectureSingleRangeApiDetailsList = new ArrayList<>();

    static {
        for (long i = 1; i <= 10; i++) {
            lectureSingleRangeApiDetailsList.add(new SingleRangeApiDetails(
                    i, "Wykład " + i, "Opis " + i
            ));
        }
    }

    public static List<SingleRangeApiDetails> laboratorySingleRangeApiDetailsList = new ArrayList<>();

    static {
        for (long i = 1; i <= 5; i++) {
            laboratorySingleRangeApiDetailsList.add(new SingleRangeApiDetails(
                    i, "Wykład " + i, "Opis " + i
            ));
        }
    }

    public static List<SlideApi> lectureSlideApiList = new ArrayList<>();

    static {
        /*for (long i = 1; i <= 5; i++) {
            lectureSlideApiList.add(new SlideApi(i, new byte[0]));
        }*/
    }
}
