package com.voroniuk.delivery.web.command;

import com.voroniuk.delivery.Path;
import com.voroniuk.delivery.db.dao.CityDAO;
import com.voroniuk.delivery.db.dao.OrderDAO;
import com.voroniuk.delivery.db.entity.*;
import com.voroniuk.delivery.utils.Calculations;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class EditCommand extends Command {
    private static final Logger LOG = Logger.getLogger(EditCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOG.debug("Command starts");
        CityDAO cityDAO = new CityDAO();
        OrderDAO orderDAO = new OrderDAO();


        int id;

        try {
            id = Integer.parseInt(req.getParameter("delivery_id"));
        } catch (NumberFormatException e) {
            return Path.COMMAND__ACCOUNT;
        }

        Delivery delivery = orderDAO.findDeliveryById(id);

        City origin = delivery.getOrigin();
        City destination = delivery.getDestination();
        String adress = delivery.getAdress();
        CargoType cType = delivery.getType();
        int weight = delivery.getWeight();
        int volume = delivery.getVolume();
        int cost = (int) delivery.getCost();

        req.setAttribute("delivery_id", id);
        req.setAttribute("origin", origin);
        req.setAttribute("destination", destination);
        req.setAttribute("adress", adress);
        req.setAttribute("cType", cType);
        req.setAttribute("weight", weight);
        req.setAttribute("volume", volume);
        req.setAttribute("cost", cost);


        LOG.debug("Command finished");

        return Path.PAGE__EDIT;
    }
}