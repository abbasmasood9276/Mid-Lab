import java.util.ArrayList;
import java.util.List;

// Subject interface
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

// CricketMatch class
class CricketMatch {
    private String matchDetails;

    CricketMatch(String matchDetails) {
        this.matchDetails = matchDetails;
    }

    String getMatchDetails() {
        return matchDetails;
    }
}

// Observer interface
interface Observer {
    void update(CricketMatch match);
}

// ConcreteSubject class
class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private CricketMatch currentMatch;

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(currentMatch);
        }
    }

    public void setMatch(CricketMatch match) {
        this.currentMatch = match;
        notifyObservers();
    }
}

// ConcreteObserver class
class ConcreteObserver implements Observer {
    private CricketMatch currentMatch;

    @Override
    public void update(CricketMatch match) {
        this.currentMatch = match;
        // Update the observer's display or perform any other necessary actions
        System.out.println("Observer updated with match details: " + match.getMatchDetails());
    }
}

// MainScreen class
class MainScreen implements Observer {
    private ConcreteSubject subject;

    MainScreen(ConcreteSubject subject) {
        this.subject = subject;
        subject.attach(this);
    }

    void displayLiveMatches(List<CricketMatch> matches) {
        // Display the list of live matches
        System.out.println("Main Screen: Live Matches - " + matches);
    }

    void selectMatch(CricketMatch match) {
        // Transition to the ball-by-ball coverage screen
        BallByBallCoverageScreen coverageScreen = new BallByBallCoverageScreen(subject);
        subject.setMatch(match); // Simulate selecting a match
    }

    @Override
    public void update(CricketMatch match) {
        // Handle updates from the subject
        System.out.println("Main Screen: Received match update - " + match.getMatchDetails());
    }
}

// BallByBallCoverageScreen class
class BallByBallCoverageScreen implements Observer {
    private ConcreteSubject subject;

    BallByBallCoverageScreen(ConcreteSubject subject) {
        this.subject = subject;
        subject.attach(this); // Subscribe to updates
    }

    @Override
    public void update(CricketMatch match) {
        // Display ball-by-ball coverage
        System.out.println("BallByBallCoverageScreen: Displaying ball-by-ball coverage for " + match.getMatchDetails());
    }
}

public class Main {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        MainScreen mainScreen = new MainScreen(subject);

        List<CricketMatch> liveMatches = new ArrayList<>();
        liveMatches.add(new CricketMatch("Pakistan vs India"));
        liveMatches.add(new CricketMatch("Australia vs England"));

        mainScreen.displayLiveMatches(liveMatches);

        // Simulate user clicking on a match
        mainScreen.selectMatch(liveMatches.get(0));

        // Simulate a new match starting and updating both screens
        CricketMatch newMatch = new CricketMatch("Australia vs England");
        subject.setMatch(newMatch);
    }
}
