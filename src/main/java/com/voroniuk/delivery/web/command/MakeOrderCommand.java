package com.voroniuk.delivery.web.command;

import com.voroniuk.delivery.Path;
import com.voroniuk.delivery.db.dao.CityDAO;
import com.voroniuk.delivery.db.dao.OrderDAO;
import com.voroniuk.delivery.db.entity.CargoType;
import com.voroniuk.delivery.db.entity.City;
import com.voroniuk.delivery.db.entity.Delivery;
import com.voroniuk.delivery.db.entity.User;
import com.voroniuk.delivery.utils.Calculations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MakeOrderCommand extends Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        CityDAO cityDAO = new CityDAO();
        OrderDAO orderDAO = new OrderDAO();

        String forward = Path.PAGE__USER_ACCOUNT;

        String adress = req.getParameter("adress");
        CargoType cType = CargoType.valueOf(req.getParameter("type"));
        int destId = 0;
        int weight;
        int length;
        int width;
        int height;
        int volume;
        int cost;

        try {
            destId = Integer.parseInt(req.getParameter("cityInp"));
            weight = Integer.parseInt(req.getParameter("weight"));
            length = Integer.parseInt(req.getParameter("length"));
            width = Integer.parseInt(req.getParameter("width"));
            height = Integer.parseInt(req.getParameter("height"));
        } catch (NumberFormatException e) {
            return forward;
        }

        City currentCity = cityDAO.findCityById(Integer.parseInt(req.getParameter("current")));
        City destination = cityDAO.findCityById(destId);

        volume = Calculations.getVolume(length, width, height);
        cost = Calculations.getCost(currentCity, destination, weight, volume);

        if (req.getParameter("calculate") != null) {
            req.setAttribute("destination", destination);
            req.setAttribute("adress", adress);
            req.setAttribute("cType", cType);
            req.setAttribute("weight", weight);
            req.setAttribute("length", length);
            req.setAttribute("width", width);
            req.setAttribute("height", height);

            req.setAttribute("cost", cost);

            return forward;
        }

        Delivery delivery = new Delivery();
        delivery.setUser((User) req.getSession().getAttribute("user"));
        delivery.setOrigin(currentCity);
        delivery.setDestination(destination);
        delivery.setAdress(adress);
        delivery.setType(cType);
        delivery.setWeight(weight);
        delivery.setVolume(volume);
        delivery.setCost(cost);

        orderDAO.saveDelivery(delivery);


        return forward;
    }

}
