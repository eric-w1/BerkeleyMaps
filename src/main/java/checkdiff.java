import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
public class checkdiff {

    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

    public static void main(String[] args) {
//        String str = "a b c 0 1 !!";
//        System.out.print(cleanString(str));


        String actual = "Earthly Coffee, Earthly Goods, Earth Sciences and Map Library, Earth Song Tuning Fork, East Bay Center for the Blind, East Bay Community Law Center, East Bay Depot For Creative Reuse, East Bay Liquors, East Bay Media Center, East Bay Nursery, East Bay Regional Park Fire Department, East Bay Spice Company, Eastern City Cafe, Eastern Classics, Eastern Supply, East Gate, Eastwind Books of Berkeley, Easy Creole, eatsa, Eat @ Thai, Ebenezer Missionary Baptist Church, E-22 Cafe, Eclipxe, Ecole Bilingue, Ecology CenterFarmer's Market, EconoGas, ecoPartners, LLC, Eddie's Liquor Video, Edible Arrangements, Education Psychology Library, El Burro Picante, Elder, Elder & Pine, Elegant Nails, Elephant Bar Restaurant, 7 Eleven, 7 eleven, 7-Eleven, Elixir, Elks Club Building, Ellis Ace Hardware, Ellsworth Structure, Elmwood, Elmwood Cafe, Elmwood Care Center, Elmwood Laundry, Elmwood Nursing and Rehab Center, Elmwood Station Berkeley Post Office, Elmwood Stationery, Elmwood Theater, Emerville City Hall, Emerybay Cafe, Emeryville, Emeryville Bayer Healthcare, Emeryville Center of Community Life, Emeryville Child Development Center, Emeryville City Hall, Emeryville Market, Emilia's Pizzeria, Emmanuel Presbyterian Church, Empty Gate Zen Center, Enhance A Village, Ennor's Restaurant Building, Enoteca Molinari, Enterprise, Enterprise Rent-a-car, Enterprise Rent-A-Car, Enterprise Rent-a-Car, Environmental Design Archives, Environmental Design Library, Epoch Frameworks, Epworth West Lot, Equator Coffee, Era M. Casey Center, Espresso Experience, Espresso Roma, Estates 31-010 Dam, Ethiopian Market, Ethnic Studies Library, Euclid Av & #1152, Euclid Av & #1151, Euclid Av & Bayview Pl, Euclid Av & Bret Harte Path, Euclid Av & Cedar St, Euclid Av & Codornices Park, Euclid Av & Cragmont Av, Euclid Av & Eunice St, Euclid Av & Hawthrone Ter, Euclid Av & Hearst Av, Euclid Av & Hilgard Av, Euclid Av & Le Conte Av, Euclid Av & Ridge Rd, Euclid Av & Rose Walk, Euclid Av & Vine Ln, Euclid Av & Virginia St, Eudemonia, Eunice Gourmet Cafe, Eureka Peak, Euromix Delicatessen, Everett and Jones, Evergreen Baptist Church, Evergreen Entrance, Evolution Home Furnishings, Express, Ex'pression College for Digital Arts, Extreme Pizza, EZ Laundry, E-Z Stop Deli";
        Scanner sca = new Scanner(actual);
        sca.useDelimiter(", ");
        ArrayList<String> expectedl = new ArrayList();
        ArrayList<String> actuall = new ArrayList();
        while(sca.hasNext()) {
            actuall.add(sca.next());
        }
        String expected = "E-22 Cafe, Earth Sciences and Map Library, Earth Song Tuning Fork, Earthly Coffee, Earthly Goods, East Bay Regional Park Fire Department, East Bay Center for the Blind, East Bay Community Law Center, East Bay Spice Company, East Bay Depot For Creative Reuse, East Bay Liquors, East Bay Media Center, East Bay Nursery, East Gate, Eastern City Cafe, Eastern Classics, Eastern Supply, Eastwind Books of Berkeley, Easy Creole, Eat @ Thai, eatsa, Ebenezer Missionary Baptist Church, Eclipxe, ecoPartners, LLC, Ecole Bilingue, Ecology CenterFarmer's Market, EconoGas, Eddie's Liquor Video, Education Psychology Library, Edible Arrangements, El Burro Picante, Elder, Elder & Pine, Elephant Bar Restaurant, 7-Eleven, Elegant Nails, Elixir, Elks Club Building, Ellsworth Structure, Ellis Ace Hardware, Elmwood, Elmwood Station Berkeley Post Office, Elmwood Stationery, Elmwood Care Center, Elmwood Cafe, Elmwood Theater, Elmwood Laundry, Elmwood Nursing and Rehab Center, Empty Gate Zen Center, Emerville City Hall, Emerybay Cafe, Emeryville, Emeryville Bayer Healthcare, Emeryville Center of Community Life, Emeryville Child Development Center, Emeryville City Hall, Emeryville Market, Emilia's Pizzeria, Emmanuel Presbyterian Church, Enterprise, Enterprise Rent-a-car, Enterprise Rent-A-Car, Enterprise Rent-a-Car, Environmental Design Archives, Environmental Design Library, Enhance A Village, Ennor's Restaurant Building, Enoteca Molinari, Epworth West Lot, Epoch Frameworks, Equator Coffee, Era M. Casey Center, Espresso Roma, Espresso Experience, Estates 31-010 Dam, Ethiopian Market, Ethnic Studies Library, Eureka Peak, Euromix Delicatessen, Euclid Av & #1152, Euclid Av & #1151, Euclid Av & Bayview Pl, Euclid Av & Bret Harte Path, Euclid Av & Ridge Rd, Euclid Av & Rose Walk, Euclid Av & Cragmont Av, Euclid Av & Cedar St, Euclid Av & Codornices Park, Euclid Av & Eunice St, Euclid Av & Virginia St, Euclid Av & Vine Ln, Euclid Av & Hawthrone Ter, Euclid Av & Hearst Av, Euclid Av & Hilgard Av, Euclid Av & Le Conte Av, Eudemonia, Eunice Gourmet Cafe, Everett and Jones, Evergreen Baptist Church, Evergreen Entrance, Evolution Home Furnishings, Express, Ex'pression College for Digital Arts, Extreme Pizza, E-Z Stop Deli, EZ Laundry";
        Scanner sce = new Scanner(expected);
        sce.useDelimiter(", ");
        while(sce.hasNext()) {
            expectedl.add(sce.next());
        }
        actuall.removeAll(expectedl);
        System.out.print(actuall);

    }

}
