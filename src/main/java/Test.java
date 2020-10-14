import com.voroniuk.delivery.db.dao.*;
import com.voroniuk.delivery.db.entity.Delivery;
import com.voroniuk.delivery.db.entity.DeliveryStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Test {
    public static void main(String[] args) {

        CityDAO cityDAO = new CityDAO();
        UserDAO userDAO = new UserDAO();
        ResourceDAO resourceDAO = new ResourceDAO();
        OrderDAO orderDAO = new OrderDAO();

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Date d1, d2 ;

        d1=d2 = new Date(0);

        try {
            d1 = format.parse("14.10.2020");
            d2 = format.parse("15.10.2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = d2.getTime() - d1.getTime();

        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        System.out.println(days);

        System.out.println(diff);

    }
}
