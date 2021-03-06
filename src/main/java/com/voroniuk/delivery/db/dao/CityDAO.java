package com.voroniuk.delivery.db.dao;

import com.voroniuk.delivery.db.entity.City;
import com.voroniuk.delivery.db.entity.Country;
import com.voroniuk.delivery.db.entity.Region;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * DAO for save and retrieve City, Region, Country from DB
 *
 * @author M. Voroniuk
 */

public class CityDAO {

    private static final Logger LOG = Logger.getLogger(CityDAO.class);

    private ResourceDAO resourceDAO;

    public CityDAO() {
        this.resourceDAO = resourceDAO = new ResourceDAO();
    }

    /**
     * Add country to DB
     * @param country
     */

    public void addCountry(Country country) {

        for (Locale locale : country.getNames().keySet()) {
            if (resourceDAO.getResourceIdByTranslation(country.getName(locale)) > 0) {
                throw new IllegalArgumentException("Country " + country.getName(locale) + " already exists");
            }
        }

        String sql = "INSERT INTO countries (name_resource_id) VALUE (?)";
        int resId;

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            resId = resourceDAO.addResource();
            statement.setInt(1, resId);

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    country.setId(resultSet.getInt(1));
                    country.setNameResourceId(resId);
                }
            }

            for (Locale locale : country.getNames().keySet()) {
                resourceDAO.addTranslation(resId, locale, country.getName(locale));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LOG.info("Country " + country.getName(Locale.getDefault()) + " added");
    }

    /**
     * Add Region to DB
     * @param region
     */
    public void addRegion(Region region) {

        for (Locale locale : region.getNames().keySet()) {
            if (resourceDAO.getResourceIdByTranslation(region.getName(locale)) > 0) {
                throw new IllegalArgumentException("Region " + region.getName(locale) + " already exists");
            }
        }

        String sql = "INSERT INTO regions (name_resource_id, country) VALUE (?, ?)";
        int resId;

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            resId = resourceDAO.addResource();
            statement.setInt(1, resId);
            statement.setInt(2, region.getCountryId());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    region.setId(resultSet.getInt(1));
                    region.setNameResourceId(resId);
                }
            }

            for (Locale locale : region.getNames().keySet()) {
                resourceDAO.addTranslation(resId, locale, region.getName(locale));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LOG.info("Region " + region.getName(Locale.getDefault()) + " added");

    }

    public void addCity(City city) {

        for (Locale locale : city.getNames().keySet()) {
            City tryFind = findCityByName(city.getName(locale));
            if (tryFind != null && tryFind.getRegionId() == city.getRegionId()) {
                throw new IllegalArgumentException("City " + city.getName(locale) + " already exists");
            }
        }

        String sql = "INSERT INTO cities (region, name_resource_id, longitude, latitude) VALUE (?, ?, ?, ?)";
        int resId;

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            resId = resourceDAO.addResource();

            statement.setInt(1, city.getRegionId());
            statement.setInt(2, resId);
            statement.setDouble(3, city.getLongitude());
            statement.setDouble(4, city.getLatitude());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    city.setId(resultSet.getInt(1));
                    city.setNameResourceId(resId);
                }
            }

            for (Locale locale : city.getNames().keySet()) {
                resourceDAO.addTranslation(resId, locale, city.getNames().get(locale));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LOG.info("City " + city.getName(Locale.getDefault()) + " added");
    }

    public City findCityByName(String name) {

        int resourceId = resourceDAO.getResourceIdByTranslation(name);

        if (resourceId < 0) {
            return null;
        }

        String sql = "SELECT id, region, longitude, latitude FROM cities WHERE name_resource_id = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setInt(1, resourceId);

            statement.executeQuery();

            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    int regionId = resultSet.getInt(2);
                    double longitude = resultSet.getDouble(3);
                    double latitude = resultSet.getDouble(4);
                    Map<Locale, String> names = resourceDAO.getTranslations(resourceId);
                    City city = new City();
                    city.setId(id);
                    city.setRegionId(regionId);
                    city.setLongitude(longitude);
                    city.setLatitude(latitude);
                    city.setNames(names);
                    city.setNameResourceId(resourceId);
                    return city;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        LOG.warn("Can't find city " + name);
        return null;
    }


    public City findCityById(int id) {
        String sql = "SELECT region, name_resource_id, longitude, latitude FROM cities WHERE id = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeQuery();

            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {

                    int regionId = resultSet.getInt(1);
                    int resourceId = resultSet.getInt(2);
                    double longitude = resultSet.getDouble(3);
                    double latitude = resultSet.getDouble(4);

                    Map<Locale, String> names = resourceDAO.getTranslations(resourceId);

                    City city = new City();
                    city.setId(id);
                    city.setRegionId(regionId);
                    city.setLongitude(longitude);
                    city.setLatitude(latitude);
                    city.setNames(names);
                    city.setNameResourceId(resourceId);
                    return city;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    public Region findRegionById(int id) {

        String sql = "SELECT name_resource_id, country FROM regions WHERE id = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setInt(1, id);

            statement.executeQuery();

            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    int resourceId = resultSet.getInt(1);
                    int countryId = resultSet.getInt(2);

                    Map<Locale, String> names = resourceDAO.getTranslations(resourceId);

                    Region region = new Region();
                    region.setNames(names);
                    region.setCountryId(countryId);
                    region.setNameResourceId(resourceId);

                    return region;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public Country findCountryById(int id) {
        String sql = "SELECT name_resource_id FROM countries WHERE id = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeQuery();

            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    int resourceId = resultSet.getInt(1);
                    Map<Locale, String> names = resourceDAO.getTranslations(resourceId);

                    Country country = new Country();
                    country.setNameResourceId(resourceId);
                    country.setName(names);
                    return country;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void deleteCountry(Country country) {
        String name = country.getName(Locale.getDefault());
        resourceDAO.deleteResource(country.getNameResourceId());
        LOG.debug("Country " + name + " deleted");
    }

    public void deleteRegion(Region region) {
        String name = region.getName(Locale.getDefault());
        resourceDAO.deleteResource(region.getNameResourceId());
        LOG.debug("Region " + name + " deleted");
    }

    public void deleteCity(City city) {
        String name = city.getName(Locale.getDefault());
        resourceDAO.deleteResource(city.getNameResourceId());
        LOG.debug("City " + name + " deleted");
    }


    public List<City> findAllCities() {
        List<City> result = new LinkedList<>();
        String sql =    "select cities.id, region, name_resource_id, longitude, latitude, lang, country, translation " +
                        "from cities \n" +
                        "join resources on name_resource_id=resources.id\n" +
                        "join translations on resource_id=resources.id\n" +
                        "join locales on locales.id=locale_id\n" +
                        "ORDER BY cities.id";


        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            int lastId = 0;
            City city = new City();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);

                String lang = resultSet.getString(6);
                String country = resultSet.getString(7);
                String translation = resultSet.getString(8);

                if (id != lastId) {
                    if (lastId > 0) {
                        result.add(city);
                    }
                    lastId = id;
                    int regionId = resultSet.getInt(2);
                    int resourceId = resultSet.getInt(3);
                    double longitude = resultSet.getDouble(4);
                    double latitude = resultSet.getDouble(5);

                    city = new City();
                    city.setId(id);
                    city.setRegionId(regionId);

                    city.setLongitude(longitude);
                    city.setLatitude(latitude);
                    city.getNames().put(new Locale(lang, country), translation);
                    city.setNameResourceId(resourceId);
                } else {
                    city.getNames().put(new Locale(lang, country), translation);
                }
            }

        } catch (SQLException e) {
            LOG.warn(e);
        }
        return result;
    }

    public List<City> findCitiesByRegionId(int regionId) {
        List<City> result = new LinkedList<>();
        String sql =    "select cities.id, region, name_resource_id, longitude, latitude, lang, country, translation " +
                        "from cities \n" +
                        "join resources on name_resource_id=resources.id\n" +
                        "join translations on resource_id=resources.id\n" +
                        "join locales on locales.id=locale_id\n" +
                        "WHERE region=?\n" +
                        "ORDER BY cities.id";


        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setInt(1, regionId);

            statement.executeQuery();

            try(ResultSet resultSet = statement.getResultSet()) {
                int lastId = 0;
                City city = new City();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);

                    String lang = resultSet.getString(6);
                    String country = resultSet.getString(7);
                    String translation = resultSet.getString(8);

                    if (id != lastId) {
                        if (lastId > 0) {
                            result.add(city);
                        }
                        lastId = id;

                        int resourceId = resultSet.getInt(3);
                        double longitude = resultSet.getDouble(4);
                        double latitude = resultSet.getDouble(5);

                        city = new City();
                        city.setId(id);
                        city.setRegionId(regionId);

                        city.setLongitude(longitude);
                        city.setLatitude(latitude);
                        city.getNames().put(new Locale(lang, country), translation);
                        city.setNameResourceId(resourceId);
                    } else {
                        city.getNames().put(new Locale(lang, country), translation);
                    }
                }
            }
        } catch (SQLException e) {
            LOG.warn(e);
        }
        return result;
    }

    public List<Region> findAllRegions() {
        List<Region> result = new LinkedList<>();
        String sql =    "select regions.id, regions.country, name_resource_id, lang, locales.country, translation from regions " +
                        "join resources on name_resource_id=resources.id " +
                        "join translations on resource_id=resources.id " +
                        "join locales on locales.id=locale_id " +
                        "ORDER BY regions.id;";


        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            int lastId = 0;
            Region region = new Region();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);

                String lang = resultSet.getString(4);
                String country = resultSet.getString(5);
                String translation = resultSet.getString(6);

                if (id != lastId) {
                    if (lastId > 0) {
                        result.add(region);
                    }
                    lastId = id;
                    int countryId = resultSet.getInt(2);
                    int resourceId = resultSet.getInt(3);

                    region = new Region();
                    region.setId(id);
                    region.setCountryId(countryId);

                    region.getNames().put(new Locale(lang, country), translation);
                    region.setNameResourceId(resourceId);
                } else {
                    region.getNames().put(new Locale(lang, country), translation);
                }
            }

        } catch (SQLException e) {
            LOG.warn(e);
        }
        return result;
    }

    /**
     * Find distance between two cities with considering Earth shape
     * @param cityA first city
     * @param cityB second city
     * @return distance in km
     */
    public double findDistance(City cityA, City cityB) {
        return findDistance(cityA.getLongitude(), cityA.getLatitude(), cityB.getLongitude(), cityB.getLatitude());
    }

    /**
     * Find distance between two points on Earth
     * @param lonA point A longitude
     * @param latA point A latitude
     * @param lonB point B longitude
     * @param latB point B latitude
     * @return distance in km
     */
    private double findDistance(double lonA, double latA, double lonB, double latB) {
        double res = 0;
        String sql = "SELECT ST_Distance_Sphere(Point(?,?),Point(?,?)) as distance";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setDouble(1, lonA);
            statement.setDouble(2, latA);
            statement.setDouble(3, lonB);
            statement.setDouble(4, latB);

            statement.executeQuery();

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    res = resultSet.getDouble(1) / 1000;
                }
            }
        } catch (SQLException e) {
            LOG.warn(e);
        }
        return res;
    }

}
