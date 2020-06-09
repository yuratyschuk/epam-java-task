package servletLayerTest;

import com.demo.model.Places;
import com.demo.model.Route;
import com.demo.model.Train;
import com.demo.model.Trip;
import com.demo.model.utils.TrainType;
import com.demo.service.RouteService;
import com.demo.service.TrainService;
import com.demo.service.TripService;
import com.demo.servlet.TripServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TripServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    TripService tripService;

    @Mock
    RouteService routeService;

    @Mock
    TrainService trainService;

    @InjectMocks
    TripServlet tripServlet;

    Route route;

    Trip trip;

    List<Trip> tripList;

    String[] stopPlaceId;

    @Before
    public void setup() {
        route = new Route();
        route.setId(1);

        trip = new Trip();
        trip.setId(1);
        tripList = new ArrayList<>();

        tripList.add(trip);
        stopPlaceId = new String[1];
        stopPlaceId[0] = "1";
    }


    @Test
    public void testDoGetActionSearch() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripSearch");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        tripServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGetActionDetails() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripDetails");
        when(request.getParameter("tripId")).thenReturn("4");
        when(tripService.getById(anyInt())).thenReturn(new Trip());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        tripServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("tripId");
        verify(request, times(1)).setAttribute(eq("trip"), any(Trip.class));

        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGetActionTripAdd() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripAdd");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(trainService.getAll()).thenReturn(anyList());

        tripServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGetActionTripUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripUpdate");
        when(request.getParameter("tripId")).thenReturn("4");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(tripService.getById(anyInt())).thenReturn(new Trip());
        when(tripService.getAll()).thenReturn(anyList());
        tripServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("tripId");
        verify(request, times(1)).setAttribute(anyString(), any(Trip.class));
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGetActionTripDelete() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripDelete");
        when(request.getParameter("tripId")).thenReturn("4");
        when(tripService.deleteById(anyInt())).thenReturn(true);

        tripServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("tripId");
        verify(response, times(1)).sendRedirect(anyString());

    }

    @Test
    public void testDoGetActionTripList() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripList");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        tripServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPostSearchResults() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("searchTrip");
        when(request.getParameter("from")).thenReturn("1");
        when(request.getParameter("to")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(routeService.getByDeparturePlaceIdAndArrivalPlaceId(anyInt(), anyInt())).thenReturn(route);
        when(tripService.getByRouteId(anyInt())).thenReturn(anyList());

        tripServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("from");
        verify(request, times(1)).getParameter("to");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPostAddRouteNotNullTrainTypeCargo() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripAdd");
        when(request.getParameter("departureTime")).thenReturn("2020-11-20");
        when(request.getParameter("arrivalTime")).thenReturn("2020-11-20");
        when(request.getParameter("numberOfCarriages")).thenReturn("2");
        when(request.getParameter("trainId")).thenReturn("2");
        when(request.getParameter("trainType")).thenReturn(TrainType.CARGO.toString());
        when(request.getParameter("departurePlaceId")).thenReturn("1");
        when(request.getParameter("arrivalPlaceId")).thenReturn("2");
        when(routeService.getByDeparturePlaceIdAndArrivalPlaceId(anyInt(), anyInt())).thenReturn(route);
        when(tripService.save(any(Trip.class))).thenReturn(trip);
        when(request.getParameterValues("stopPlaceId")).thenReturn(stopPlaceId);

        tripServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("arrivalTime");
        verify(request, times(1)).getParameter("departureTime");
        verify(request, times(1)).getParameter("numberOfCarriages");
        verify(request, times(1)).getParameter("trainId");
        verify(request, times(1)).getParameter("trainType");
        verify(request, times(1)).getParameter("departurePlaceId");
        verify(request, times(1)).getParameter("arrivalPlaceId");
        verify(request, times(1)).getParameterValues("stopPlaceId");
        verify(response, times(1)).sendRedirect(anyString());
    }


    @Test
    public void testDoPostUpdateRouteNullAndTrainTypePassenger() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("tripUpdate");
        when(request.getParameter("tripId")).thenReturn("2");
        when(request.getParameter("departureTime")).thenReturn("2020-11-20");
        when(request.getParameter("arrivalTime")).thenReturn("2020-11-20");
        when(request.getParameter("numberOfCarriages")).thenReturn("2");
        when(request.getParameter("trainId")).thenReturn("2");
        when(request.getParameter("trainType")).thenReturn(TrainType.PASSENGER.toString());
        when(request.getParameter("price")).thenReturn("200");
        when(request.getParameter("numberOfPlaces")).thenReturn("20");
        when(request.getParameter("departurePlaceId")).thenReturn("1");
        when(request.getParameter("arrivalPlaceId")).thenReturn("4");
        when(routeService.getByDeparturePlaceIdAndArrivalPlaceId(anyInt(), anyInt())).thenReturn(null);
        when(request.getParameterValues("stopPlaceId")).thenReturn(stopPlaceId);

        tripServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("tripId");
        verify(request, times(1)).getParameter("arrivalTime");
        verify(request, times(1)).getParameter("departureTime");
        verify(request, times(1)).getParameter("numberOfCarriages");
        verify(request, times(1)).getParameter("trainId");
        verify(request, times(1)).getParameter("trainType");
        verify(request, times(1)).getParameter("price");
        verify(request, times(1)).getParameter("numberOfPlaces");
        verify(request, times(1)).getParameter("departurePlaceId");
        verify(request, times(1)).getParameter("arrivalPlaceId");

        verify(request, times(1)).getParameterValues("stopPlaceId");
        verify(response, times(1)).sendRedirect(anyString());
    }
}
