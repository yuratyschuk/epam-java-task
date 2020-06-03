package servletLayerTest;

import com.demo.model.Places;
import com.demo.model.Train;
import com.demo.model.Trip;
import com.demo.model.utils.TrainType;
import com.demo.servlet.TripServlet;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.DataInput;
import java.io.IOException;
import java.util.Date;

import static org.mockito.Mockito.*;
public class TripServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);

    HttpServletResponse response = mock(HttpServletResponse.class);

    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    String[] testString = new String[2];


    @Before
    public void setup() {
        testString[0] = "1";
        testString[1] = "2";
    }




    @Test
    public void testDoGetActionSearch() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("search");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TripServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Trip.class));
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    public void testDoGetActionDetails() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("details");
        when(request.getParameter("tripId")).thenReturn("4");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TripServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getParameter("tripId");
        verify(request, atLeastOnce()).setAttribute(eq("trip"), any(Trip.class));

        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    public void testDoGetActionTripAdd() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripAdd");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TripServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Trip.class));
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Places.class));

        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    public void testDoGetActionTripUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripUpdate");
        when(request.getParameter("tripId")).thenReturn("4");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TripServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getParameter("tripId");
        verify(request, atLeastOnce()).setAttribute(anyString(), any(Trip.class));
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Places.class));
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Train.class));

        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    public void testDoGetActionTripDelete() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripDelete");
        when(request.getParameter("tripId")).thenReturn("4");

        new TripServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getParameter("tripId");
        verify( response, atLeastOnce()).sendRedirect(anyString());

    }

    @Test
    public void testDoGetActionTripList() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripList");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TripServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Trip.class));

        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    public void testDoPostSearchResults() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("searchTrip");
        when(request.getParameter("from")).thenReturn("1");
        when(request.getParameter("to")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TripServlet().doPost(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request,  atLeastOnce()).getParameter("from");
        verify(request, atLeastOnce()).getParameter("to");
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Trip.class));

        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    public void testDoPostUpdateRouteNotNull() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("tripId")).thenReturn("4");
        when(request.getParameter("departureTime")).thenReturn("2020-11-20");
        when(request.getParameter("arrivalTime")).thenReturn("2020-11-20");
        when(request.getParameter("numberOfCarriages")).thenReturn("2");
        when(request.getParameter("trainId")).thenReturn("2");
        when(request.getParameter("trainType")).thenReturn(TrainType.PASSENGER.toString());
        when(request.getParameter("price")).thenReturn("200");
        when(request.getParameter("numberOfPlaces")).thenReturn("20");
        when(request.getParameter("departurePlaceId")).thenReturn("1");
        when(request.getParameter("arrivalPlaceId")).thenReturn("2");
        when(request.getParameterValues(eq("stopPlaceId"))).thenReturn(testString);

        new TripServlet().doPost(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getParameter("tripId");
        verify(request, atLeastOnce()).getParameter("arrivalTime");
        verify(request, atLeastOnce()).getParameter("departureTime");
        verify(request, atLeastOnce()).getParameter("numberOfCarriages");
        verify(request, atLeastOnce()).getParameter("trainId");
        verify(request, atLeastOnce()).getParameter("trainType");
        verify(request, atLeastOnce()).getParameter("price");
        verify(request, atLeastOnce()).getParameter("numberOfPlaces");
        verify(request, atLeastOnce()).getParameter("departurePlaceId");
        verify(request, atLeastOnce()).getParameter("arrivalPlaceId");
        verify(request, atLeastOnce()).getParameterValues("stopPlaceId");
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }


//    @Test
//    public void testDoPostAddRouteNull() throws ServletException, IOException {
//        when(request.getParameter("action")).thenReturn("add");
//        when(request.getParameter("tripId")).thenReturn("");
//        when(request.getParameter("departureTime")).thenReturn("2020-11-20");
//        when(request.getParameter("arrivalTime")).thenReturn("2020-11-20");
//        when(request.getParameter("numberOfCarriages")).thenReturn("2");
//        when(request.getParameter("trainId")).thenReturn("2");
//        when(request.getParameter("trainType")).thenReturn(TrainType.PASSENGER.toString());
//        when(request.getParameter("price")).thenReturn("200");
//        when(request.getParameter("numberOfPlaces")).thenReturn("20");
//        when(request.getParameter("departurePlaceId")).thenReturn("1");
//        when(request.getParameter("arrivalPlaceId")).thenReturn("4");
//        when(request.getParameterValues(eq("stopPlaceId"))).thenReturn(testString);
//
//        new TripServlet().doPost(request, response);
//
//        verify(request, atLeastOnce()).getParameter("action");
//        verify(request, atLeastOnce()).getParameter("tripId");
//        verify(request, atLeastOnce()).getParameter("arrivalTime");
//        verify(request, atLeastOnce()).getParameter("departureTime");
//        verify(request, atLeastOnce()).getParameter("numberOfCarriages");
//        verify(request, atLeastOnce()).getParameter("trainId");
//        verify(request, atLeastOnce()).getParameter("trainType");
//        verify(request, atLeastOnce()).getParameter("price");
//        verify(request, atLeastOnce()).getParameter("numberOfPlaces");
//        verify(request, atLeastOnce()).getParameter("departurePlaceId");
//        verify(request, atLeastOnce()).getParameter("arrivalPlaceId");
//        verify(request, atLeastOnce()).getParameterValues("stopPlaceId");
//        verify(response, atLeastOnce()).sendRedirect(anyString());
//    }
}
