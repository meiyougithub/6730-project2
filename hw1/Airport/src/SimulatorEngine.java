//YOUR NAME HERE

import java.util.TreeSet;

public class SimulatorEngine implements EventHandler {

    private double m_currentTime;
    private TreeSet<Event> m_eventList;
    private boolean m_running;

    SimulatorEngine() {
        m_running = false;
        m_currentTime = 0.0;
        m_eventList = new TreeSet<Event>();
    }

    void run() {
        Weather weather = new Weather(Weather.SUMMER);
        m_running = true;
        while(m_running && !m_eventList.isEmpty()) {
            Event ev = m_eventList.pollFirst();
            m_currentTime = ev.getTime();

            // each event triggers the weather change
            // if the event handler is airport (not sim engine)
            if (ev.getHandler() instanceof Airport) {
                // cast the event handler to airport
                Airport airport = (Airport) ev.getHandler();
                // set the weather of airport
                airport.setWeather(Weather.change(airport.getWeather()));
            }
            ev.getHandler().handle(ev);
        }
    }

    public void handle(Event event) {
        SimulatorEvent ev = (SimulatorEvent)event;

        switch(ev.getType()) {
            case SimulatorEvent.STOP_EVENT:
                m_running = false;
                System.out.println("Simulator stopping at time: " + ev.getTime());
                break;
            default:
                System.out.println("Invalid event type");
        }
    }

    public void schedule(Event event) {
        m_eventList.add(event);
    }

    public void stop() {
        m_running = false;
    }

    public double getCurrentTime() {
        return m_currentTime;
    }
}
