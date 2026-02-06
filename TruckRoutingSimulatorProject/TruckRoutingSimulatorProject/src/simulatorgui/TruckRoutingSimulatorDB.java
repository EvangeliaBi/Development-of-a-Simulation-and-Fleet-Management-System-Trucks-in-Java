package simulatorgui;		// Η κλάση αυτή ανήκει σε αυτό το πακέτο, δηλαδή ανήκει στο GUI layer, καθώς διαχωρίζεται αυστηρά από το simulatorengine (business logic) και από το simulator (data access / DAO). Το GUI layer δεν περιέχει business logic, υπολογισμούς και πρόσβαση σε βάση δεδομένων, σύμφωνα με την σχεδιαστική αρχή Separation of Concerns, καθώς το GUI layer εμφανίζει δεδομένα, συλλέγει inputs και δεν κάνει υπολογισμούς με βάσει το Single Responsibility Principle (SRP).

import javax.swing.*;		// Επιτρέπει χρήση κλάσεων χωρίς πλήρως προσδιορισμένο όνομα. Κάνω import (εισάγω) το framework(την βιβλιοθήκη) swing (κλάσεις και διεπαφές για GUI συστατικά), η οποία περιέχει όλες τις κλάσεις για την δημιουργία GUI (Grafical User Interface-γραφικού περιβάλλοντος διεπαφής χρήστη/επικοινωνίας), καθώς τα συστατικά (Components) της swing περιλαμβάνουν (κουμπιά, πλαίσια κειμένου, ετικέτες, περιγράμματα, την δημιουργία τμημάτων παραθύρων με δυνατότητες κύλισης, μενού, γραμμές εργαλείων, στοιχεία ελέγχου κειμένου HTML, λεζάντες ενώ το κάθε component(συστατικό) ξεκινάει με ένα κεφαλαίο γράμμα το J και στην συνέχεια ακολουθεί με κεφαλαίο το πρώτο γράμμα το όνομα που περιγράφει την λειτουργία αυτού του συστατικού (πχ. JLabel, JButton, JTextField, JTextArea). Η πλειονότητα των συστατικών της βιβλιοθήκης swing είναι lightweight(ελαφρά), καθώς η γλώσσα Java είναι υπεύθυνη για την γραφή αυτών των components. Η superclass όλων των γραφικών στοιχείων της swing είναι η Component.
import java.awt.*;		// Κάνω import (εισάγω) το πακέτο java.awt όπου εισάγω όλες τις κλάσεις που συμπεριλαμβάνονται μέσα σε αυτό το πακέτο, όπου αυτές οι κλάσεις είναι υπεύθυνες για την δημιουργία του περιβάλλοντος διεπαφής, της δημιουργίας γραφικών και εικόνων και πιο συγκεκριμένα τα χρώματα που επιλέγω για την εφαρμογή μου, την διάταξη που καθορίζεται από τους layout managers, την δημιουργία πλαισίων, κουμπιών, ετικετών, μενού καθώς όλες αυτές οι κλάσεις είναι διεπαφές που βρίσκονατι μέσα στο Abstract Window Toolkit (AWT). Τα συστατικά της AWT είναι γραμμένα με γηγενή κώδικα, ενώ είναι heavyweight (βαριά-δυνατότητα απεικόνισης στην οθόνη σε συγκεκριμένο λειτουργικό σύστημα) αλλάζοντας δυναμικά μορφή από Windows, για παράδειγμα σε Linux ενώ γίνονται import και οι Layout managers(BorderLayout, GridBagLayout, GridBagConstraints) για την σωστή διάταξη των στοιχείων στο παράθυρο. Πιο συγκεκριμένα είναι ο τρόπος διάταξης των γραφικών στοιχείων μέσα στο panel.

// Παρακάτω μέσω του Dependency Injection (λόγω του ότι το GUI εξαρτάται από το SimulationEngine), εισάγονται εξωτερικές κλάσεις από άλλα πακέτα. Οι DAO κλάσεις (που σχετίζονται με την αποθήκευση των δεδομένων) καθώς και η κλάση SimulationEngine από το αντίστοιχο πακέτο. Στην ουσία το GUI μέσω της εισαγωγής των παρακάτω πακέτων ζητά δεδομένα από τις κλάσεις DAO και αποτελέσματα από την κλάση SimulationEngine. Γίνονται import οι DAO κλάσεις και δεν χρησιμοποιούνται απευθείας δεδομένα, διότι απομονώνουν το data source και επιτρέπουν αλλαγή DB / αρχείου / API, με αποτέλεσμα να είναι εφικτή η επεκτασιμότητα του κώδικα, το testability και κάποια ενδεχόμενη αντικατάσταση του backend. Επιπλέον γίνεται import η κλάση SimulationEngine, η οποία αποτελεί το κέντρο της επιχειρησιακής λογικής, δημιουργώντας το GUI, τροφοδοτώντας με δεδομένα το GUI και λαμβάνοντας αποτελέσματα.
import simulator.CustomerDAO;
import simulator.RouteDAO;
import simulator.TruckDAO;
import simulator.WarehouseDAO;
import simulatorengine.SimulationEngine;

import java.util.List;		// Εισάγεται η κλάση List από το πακέτο java.util, διατηρώντας την σειρά καθώς θα χρησιμοποιηθεί για τους customers, trucks και routes.
import java.util.Map;		// Γίνεται εισαγωγή η κλάση Map εκφράζοντας μία Key → Value συσχέτιση, όπου το routeCode → distance και το routeCode → profit με βάσει τον domain-driven σχεδιασμό.
import java.util.HashMap;		// Υψηλή απόδοση για lookups.
import java.util.stream.Collectors;		// Εδώ στην ουσία εισάγεται η στατική κλάση Collectors από το πακέτο java.util.stream, που επιτρέπει την χρησιμοποίηση συλλογών τελικών αποτελεσμάτων από streams. Στην ουσία το Collectors είναι μια utility class που παρέχει στατικές μεθόδους για την τελειοποίηση του αποτελέσματος ενός Stream. Χρησιμοποιούνται Streams + Collectors για καθαρότητα κώδικα, αποφυγή manual loops και για Immutable functional-style operations, καθώς με αυτό τον τρόπο διατηρείται το separation of concerns χωρίς να αλλοιώνεται το αρχικό dataset και το GUI παίρνει μόνο τα φίλτρα που χρειάζεται. Στην ουσία το import αυτό επιτρέπει functional-style συλλογή αποτελεσμάτων αποφεύγοντας μεταβλητές και for-loops για filtering, από την στιγμή που βάσει αρχιτεκτονικής το GUI παίρνει δεδομένα με φίλτρα, ενώ το SimulationEngine δουλεύει με έτοιμη, καθαρή λίστα.

public class TruckRoutingSimulatorDB extends JFrame {	  // Η public αυτή κλάση επεκτείνει την κλάση JFrame, πράγμα το οποίο σημαίνει ότι η κλάση μας αυτή είναι παράθυρο GUI της Swing κληρονομώντας όλες τις μεθόδους και ιδιότητες ενός παραθύρου (π.χ. setSize(), add(Component c), setVisible(true) κλπ), καθώς το GUI layer χειρίζεται μόνο την παρουσίαση και την αλληλεπίδραση με τον χρήστη και δεν υπολογίζει κέρδη απευθείας, αλλά συνεργάζεται με το SimulationEngine.

	// Δήλωση των GUI Components, όπου κάθε JComboBox και JButton αντιπροσωπεύει ένα "control element" στο παράθυρο. Το GUI ασχολείται μόνο μετην διεπαφή με τον χρήστη, καθώς δεν υπολογίζει κέρδη απευθείας, αφού στέλνει δεδομένα στο SimulationEngine.
	// Παρακάτω δηλώνονται Drop-down λίστες επιλογών για τον χρήστη, χωρίς την πληκτρολόγηση δεδομένων.
	// Παρακάτω δημιουργούνται νέα αντικείμενα δηλαδή πτυσσόμενα πλαίσια/κατερχόμενες λίστες μέσω της (JComboBox), το οποίο είναι ένα ειδικό πλαίσιο που παρέχει μία σειρά επιλογών(στην προκειμένη περίπτωση περιέχει τιμές τύπου String-αλφαριθμητικά) για να διαλέξει ο χρήστης της εφαρμογής καθώς είναι ένα light component ενώ κληρονομείται από την κλάση JComponent.
    private JComboBox<String> warehouseTypeCombo;	// Επιλογή τύπου warehouse (π.χ. Short / Long).
    private JComboBox<String> warehouseCombo;		// Επιλογή συγκεκριμένου warehouse.
    private JComboBox<String> routeCombo;			// Επιλογή route (διαδρομής).
    private JComboBox<String> customerCombo;		// Επιλογή πελάτη.
    private JComboBox<String> truckCombo;			// Επιλογή τύπου φορτηγού.
    private JTextArea resultArea;					// Περιοχή κειμένου πολλαπλών γραμμών, όπου χρησιμοποιείται για εμφάνιση αποτελεσμάτων (π.χ. κέρδη, αποτελέσματα simulations), ενώ το setEditable τίθεται με την τιμή (false), διότι είναι μόνο για ανάγνωση και δεν μπορέι κάποιος να πάει να γράψει/να πληκτρολογήσει πάνω σε αυτή την περιοχή.
    private HashMap<String, Double> routeDistances;		// Χάρτης που αντιστοιχεί κάθε routeCode (π.χ. "R1") σε απόσταση (π.χ. 45.3 km), καθώς χρησιμοποιείται για υπολογισμούς κερδών και για το simulation, από την στιγμή που τα δεδομένα έρχονται από το RouteDAO (data layer) και το GUI τα χρησιμοποιεί μόνο αλλά δεν τα υπολογίζει.
    // Τα παρακάτω είναι JButton components (κουμπιά), όπου Ο χρήστης εκτελεί ενέργειες με ένα click, χωρίς να χρειάζεται να καταλάβει τον εσωτερικό αλγόριθμο.
    private JButton bestRouteButton;				// Βρίσκει αυτόματα την πιο κερδοφόρα διαδρομή.
    private JButton hourlySimButton;				// Τρέχει προσομοίωση ανά ώρα.
    private JButton stochasticSimButton;			// Τρέχει στοχαστική προσομοίωση.
    private JButton allRoutesProfitButton; 			// Υπολογίζει το κέρδος για όλες τις διαδρομές.

    public TruckRoutingSimulatorDB() {		// Εδώ δημιουργείται ο constructor της κλάσης TruckRoutingSimulatorDB, με βασικό σκοπό να αρχικοποιήσει το GUI παράθυρο μόλις δημιουργηθεί ένα αντικείμενο αυτής της κλάσης, καθώς από εδώ ξεκινά η διαμόρφωση του παραθύρου και των βασικών χαρακτηριστικών του GUI.
        setTitle("Truck Routing Simulator for Maximizing Profits");		// Μέσω χρήσης της μεθόδου setTitle και με παράμετρο εισόδου ένα αλφαριθμητικό δεδομένο καθορίζεται ο τίτλος του παραθύρου, που θα εμφανίζεται στο JFrame.
        setSize(1000, 700);			// Ρυθμίζει πλάτος και ύψος του παραθύρου σε pixels με απώτερο σκοπό να υπάρχει αρκετός χώρος για όλα τα στοιχεία GUI (dropdowns, buttons, text area), ενώ το μέγεθος επιλέγεται ώστε να χωράει panel με GridBagLayout, κουμπιά, και την περιοχή αποτελεσμάτων, προσφέροντας comfortable layout χωρίς scrolling στο βασικό panel.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// Μέσω χρήσης της μεθόδου setDefaultCloseOperation και βάζοντας ως παράμετρο εισόδου στην μέθοδο την κλάση JFrame, καθορίζω τί θα συμβεί στην εφαρμογή όταν ο χρήστης κλείσει το παράαθυρο με αποτέλεσμα τον πλήρη τερματισμό της εφαρμογής, πατώντας το κουμπί Χ (το εικονίδιο του κλεισίματος) βγαίνει από το αντίστοιχο παράθυρο, καθώς αυτό είναι χρήσιμο για desktop εφαρμογές, ώστε να μην μένει η Java Virtual Machine (JVM) ενεργή στο background. Στην ουσία αυτό προλαμβάνει “κρυφές διεργασίες” που καταναλώνουν μνήμη, διατηρώντας το GUI καθαρό και ασφαλές.
        setLocationRelativeTo(null);		// Ρύθμιση του παραθύρου θέσης στην οθόνη, όπου το null σημαίνει ότι κεντράρεται το παράθυρο στην οθόνη ανεξαρτήτως αν ο χρήστης έχει πολλαπλές οθόνες, βελτιώνοντας την χρηστικότητα, γιατί το παράθυρο εμφανίζεται μεσαίο και ορατό αμέσως, καθώς αποφεύγονται περιπτώσεις όπου το παράθυρο ανοίγει “εκτός οθόνης” ή στην πάνω-αριστερή γωνία.

        // Αρχικοποίηση των COMPONENTS
        warehouseTypeCombo = new JComboBox<>(new String[]{"Short", "Long"});	// Δημιουργείται ένα drop-down (combo box) με προκαθορισμένες τιμές "Short" και "Long", καθώς είναι ορισμένο ως JComboBox<String>, όπου μόνο strings επιτρέπονται ως επιλογές. Ο χρήστης επιλέγει τύπο αποθήκης και αυτό επηρεάζει ποιες διαδρομές ή πελάτες θα φορτωθούν αργότερα.
        // Παρακάτω δημιουργούνται άδεια JComboBox, καθώς τα στοιχεία αυτά δεν είναι προκαθορισμένα όπως το warehouseTypeCombo, αλλά φορτώνονται runtime (κατά την εκτέλεση του simulation) από τη βάση δεδομένων μέσω DAO, αναλόγως του τί έχει επιλέξει ο χρήστης από τις drop-down lists. Στην ουσία πρόκειται για διαχωρισμό στατικού (σταθερές επιλογές) και δυναμικού περιεχομένου (από βάση δεδομένων) για καλύτερη ευελιξία και επεκτασιμότητα.
        warehouseCombo = new JComboBox<>();		// Λίστα συγκεκριμένων αποθηκών που θα φορτωθούν δυναμικά από τη βάση (DAO).
        routeCombo = new JComboBox<>();			// Λίστα διαδρομών.
        customerCombo = new JComboBox<>();		// Λίστα πελατών.
        truckCombo = new JComboBox<>();			// Λίστα τύπων φορτηγών.
        resultArea = new JTextArea(16, 80);		// Δημιουργείται ένα πεδίο πολλαπλών γραμμών για κείμενο (text area), 16 γραμμές ύψος και 80 στήλες πλάτος, ώστε να μπορεί να εμφανίζει αναφορές, αποτελέσματα και πίνακες κερδών χωρίς scroll πολύ γρήγορα, ενώ ταυτόχρονα χρησιμοποιείται για output στον χρήστη, δηλαδή εμφάνιση αποτελεσμάτων των υπολογισμών του SimulationEngine.
        resultArea.setEditable(false);			// Εδώ απαγορεύεται η επεξεργασία του κειμένου από τον χρήστη, καθώς ο χρήστης δεν πρέπει να τροποποιήσει τα αποτελέσματα, μόνο να τα βλέπει μπορεί, διαχωρίζοντας input (combo boxes) και output (resultArea).

        
        List<TruckDAO.Truck> trucks = TruckDAO.getAllTruckTypes();	// Σε αυτό το σημείο καλείται η μέθοδος getAllTruckTypes() της κλάσης TruckDAO, όπου η κλάση TruckDAO είναι μια Data Access Object (DAO) κλάση, υπεύθυνη για την ανάκτηση δεδομένων σχετικά με φορτηγά από τη βάση δεδομένων, ενώ ταυτόχρονα η μέθοδος επιστρέφει μία λίστα από αντικείμενα τύπου TruckDAO.Truck, καθώς κάθε Truck περιέχει πληροφορίες όπως category (τύπος φορτηγού) και capacity (χωρητικότητα), με σκοπό το αποτέλεσμα να αποθηκεύεται στη μεταβλητή trucks που είναι τύπου List<TruckDAO.Truck>. Μέσω της αρχιτεκτονικής αυτής διαχωρίζεται η πρόσβαση στα δεδομένα (DAO) από το GUI, καθώς το GUI ζητά την ανάκτηση όλων των φορτηγών σύμφωνα με την αρχή separation of concerns.
        for (TruckDAO.Truck t : trucks) {		// Μέσω της enhanced for και σε κάθε επανάληψη, για κάθε φορτηγό t που βρίσκεται μέσα στη λίστα trucks, κατασκευάζεται ένα String για εμφάνιση στο combo box, όπου το t.getCategory() είναι ο τύπος του φορτηγού (π.χ. "Small", "Medium", "Large") και το t.getCapacity() είναι η χωρητικότητα του φορτηγού σε μονάδες, καθώς το τελικό string προστίθεται στο truckCombo, δηλαδή στο drop-down menu της GUI. Με αυτόν τον τρόπο ο χρήστης βλέπει την κατανοητή περιγραφή του κάθε φορτηγού, όχι ένα αντικείμενο Java, καθώς συνδέεται ο τύπος φορτηγού και η χωρητικότητα. Στην ουσία εδώ το DAO Layer ανακτά δεδομένα από την βάση (TruckDAO.getAllTruckTypes()), το GUI Layer γεμίζει το JComboBox με strings φιλικά προς τον χρήστη κι αργότερα μέσω της SimulationEngine θα χρησιμοποιηθεί το επιλεγμένο φορτηγό για υπολογισμούς κερδών και δρομολογίων.
            truckCombo.addItem(t.getCategory() + " (" + t.getCapacity() + " units)");
        }

        // Εμφάνιση κουμπιών μαζί με τα οπτικά στοιχεία, καθώς οι ActionListeners συνδέουν τα κουμπιά με τις αντίστοιχες μεθόδους, από την στιγμή που κάθε κουμπί αντιπροσωπεύει μια ξεχωριστή λειτουργία της εφαρμογής: manual route, best route, hourly simulation, stochastic simulation, profit for all routes.
        JButton calculateButton = new JButton("Calculate 6-Month Profit");		// Δημιουργείται ένα νέο αντικείμενο τύπου JButton της Swing εμφανίζοντας το κείμενο "Calculate 6-Month Profit" στον χρήστη κι έτσι ο χρήστης καταλαβαίνει ότι με το πάτημα αυτού του κουμπιού θα υπολογιστεί το 6μηνιαίο κέρδος για την επιλεγμένη διαδρομή/φορτηγό. Η δημιουργία του κουμπιού είναι GUI-only. Η λογική υπολογισμού δεν γίνεται εδώ, διότι απλώς δημιουργείται το συστατικό (component), καθώς η ενέργεια που εκτελείται όταν πατιέται το κουμπί θα συνδεθεί αργότερα μέσω του ActionListener.
        bestRouteButton = new JButton("Find Best Route Automatically");			// Το αντικείμενο bestRouteButton (που έχει δηλωθεί ως πεδίο της κλάσης) αρχικοποιείται με ένα νέο JButton, εμφανίζοντας το κείμενο "Find Best Route Automatically", όπου ο χρήστης καταλαβαίνει ότι αυτό το κουμπί θα κάνει αυτόματο υπολογισμό για την καλύτερη διαδρομή με βάση το μέγιστο κέρδος. Η αρχιτεκτονική είναι GUI-only, χωρίς business logic, διότι η σύνδεση με την πραγματική εύρεση της καλύτερης διαδρομής θα γίνει μέσω ActionListener, που θα καλέσει τη μέθοδο του SimulationEngine.
        hourlySimButton = new JButton("Run Hourly Simulation");					// Δημιουργείται κουμπί για ωριαία προσομοίωση (deterministic simulation), όπου το κείμενο στον χρήστη δείχνει ότι η προσομοίωση θα τρέξει σε επίπεδο ώρας, με αποτέλεσμα ο χρήστης να μπορεί να δει πώς αλλάζει το κέρδος ή τα trips ανά ώρα, ενώ ο πραγματικός υπολογισμός γίνεται στο SimulationEngine.
        stochasticSimButton = new JButton("Run Stochastic Optimized Simulation");		// Δημιουργείται κουμπί για στοχαστική βελτιστοποιημένη προσομοίωση (stochastic simulation), όπου το κείμενο δείχνει ότι η προσομοίωση περιλαμβάνει τυχαίες ή βελτιστοποιημένες παραμέτρους (π.χ. αβεβαιότητα στη ζήτηση ή στον χρόνο).
        allRoutesProfitButton = new JButton("Calculate Profit for All Routes"); 		// Δημιουργείται κουμπί για υπολογισμό κέρδους για όλες τις διαδρομές ταυτόχρονα, καθώς το κείμενο δείχνει ότι θα εμφανιστεί το 6μηνιαίο κέρδος για όλα τα routes, όχι μόνο για ένα επιλεγμένο route, δίνοντας στον χρήστη μια συγκεντρωτική εικόνα των αποτελεσμάτων για όλες τις διαδρομές, από την στιγμή που η υπολογιστική λογική βρίσκεται στη μέθοδο calculateProfitForAllRoutes() της SimulationEngine.

        
     // Εδώ υλοποιείται το LAYOUT, όπου όλες οι ετικέτες και τα components ευθυγραμμίζονται με GridBagLayout για καθαρή εμφάνιση, τα κουμπιά καταλαμβάνουν όλο το πλάτος για καλύτερη προσβασιμότητα και το Result area με scrollbars για μεγάλες εκτυπώσεις.
        JPanel panel = new JPanel(new GridBagLayout());		// Δημιουργία του JPanel, δηλαδή ενός container που θα περιέχει όλα τα GUI components (κουμπιά, combo boxes, text area), ενώ ταυτόχρονα ορίζεται και ο layout manager του panel σε GridBagLayout, όπου είναι είναι ένας πολύ ευέλικτος layout manager που μας επιτρέπει να τοποθετούμε components σε γραμμές και στήλες (grid), με πλήρη έλεγχο για το μέγεθος, το padding και τη στοίχιση, καθώς δηλώνεται ένα αντικείμενο GridBagConstraints gbc που θα χρησιμοποιηθεί για να ορίσουμε πώς κάθε component τοποθετείται στο grid.
        GridBagConstraints gbc;

        // Row 0 - Warehouse Type
        gbc = new GridBagConstraints();
        gbc.gridx = 0;		// στήλη 0 (αριστερά).
        gbc.gridy = 0;		// γραμμή 0 (πρώτη γραμμή).
        gbc.insets = new Insets(8, 8, 8, 8);	// padding γύρω από το component (top, left, bottom, right). Εδώ στην ουσία καθορίζεται ο ελέυθερος χώρος γύρω από το συστατικό, καθώς ο τύπος αυτής της παραμέτρου είναι η κλάση java.awt.Insets (περιέχει τα πεδία top, bottom, left, right).
        gbc.anchor = GridBagConstraints.LINE_END;	// στοιχίζει την ετικέτα στα δεξιά του κελιού (προς το component που θα είναι δίπλα). Με την χρήση του anchor, όταν το συστατικό είναι μικρότερο από τον χώρο που το περιέχει, τότε η παράμετρος αυτή καθορίζει πού θα τοποθετηθεί.
        panel.add(new JLabel("Select Warehouse Type:"), gbc);	// Προσθήκη στο panel ένα JLabel με κείμενο "Select Warehouse Type:".

        gbc = new GridBagConstraints();		// Νέα constraints για το combo box.
        gbc.gridx = 1;	// στήλη 1 (δίπλα στην ετικέτα).
        gbc.gridy = 0;	// ίδια γραμμή με την ετικέτα.
        gbc.insets = new Insets(8, 8, 8, 8);	
        gbc.fill = GridBagConstraints.HORIZONTAL;	// το combo box εκτείνεται οριζόντια για να γεμίσει το κελί. Στην ουσία καθορίζεται το μέγεθος που θα πάρει το συστατικό σε σχέση με το χώρο που το περιέχει.
        panel.add(warehouseTypeCombo, gbc);		// Προσθήκη του warehouseTypeCombo στο panel.

        // Παρακάτω για τα (Warehouse, Route, Customer, Truck) υλοποιείται η ίδια λογική με την γραμμή 0 του Warehouse Type όπου γίνονται τα εξής:
        /*
         * Αριστερά (gridx=0) μπαίνει η ετικέτα (JLabel).
		Δεξιά (gridx=1) μπαίνει το component επιλογής (JComboBox).
		Ορίζεται insets για padding και fill=HORIZONTAL για να γεμίζει το κελί οριζόντια.
		Κάθε γραμμή αυξάνει gridy κατά 1, για να πάμε στην επόμενη γραμμή.
		Επομένως: Όλα τα στοιχεία επιλογής είναι ευθυγραμμισμένα σε στήλες, με ετικέτες αριστερά και πεδία επιλογής δεξιά. 
         **/
        // Row 1 - Warehouse
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Select Warehouse:"), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(warehouseCombo, gbc);

        // Row 2 - Route
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Select Route:"), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(routeCombo, gbc);

        // Row 3 - Customer
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Select Customer:"), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(customerCombo, gbc);

        // Row 4 - Truck
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Select Truck Type:"), gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(truckCombo, gbc);

        // Row 5 - Calculate button
        /*
         *	Παρακάτω για τις γραμμές 5-9 που αντιστοιχούν στα κουμπιά ισχύει η ίδια λογική που εφαρμόζεται για την γραμμή 5 του κουμπιού (Calculate Button).
         *	Ανάλογη εφαρμογή και για τα υπόλοιπα κουμπιά (bestRouteButton, hourlySimButton, stochasticSimButton, allRoutesProfitButton) 
         *	που βρίσκονται στις επόμενες γραμμές (gridy = 6, 7, 8, 9). 
         **/
        gbc = new GridBagConstraints();
        gbc.gridx = 0;		// εδώ αναφέρεται στην πρώτη στήλη.
        gbc.gridy = 5;		// γραμμή 5
        gbc.gridwidth = 2;	// καλύπτει και τις δύο στήλες, δηλαδή το κουμπί εκτείνεται από τη στήλη της ετικέτας μέχρι τη στήλη του combo box.
        gbc.insets = new Insets(10, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;	// το κουμπί εκτείνεται οριζόντια.
        panel.add(calculateButton, gbc);

        // Row 6 - Best Route
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(bestRouteButton, gbc);

        // Row 7 - Hourly Simulation
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(hourlySimButton, gbc);

        // Row 8 - Stochastic Simulation
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(stochasticSimButton, gbc);

        // Row 9 - All Routes Profit
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(allRoutesProfitButton, gbc);
        
        // Row 10 - Results area
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;	// εκτείνεται σε όλο το πλάτος του panel.
        // Παρακάτω με το weightx = 1.0 και weighty = 1.0, δίνει ελαστικότητα στο component, ώστε να μεγαλώνει όταν το παράθυρο αλλάζει μέγεθος.
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(12, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;		// γεμίζει και οριζόντια και κατακόρυφα τον χώρο που του δίνεται.
        panel.add(new JScrollPane(resultArea), gbc);	// Προσθήκη ενός JScrollPane γύρω από το resultArea για να έχουμε scrollbars όταν το κείμενο ξεπεράσει το μέγεθος.

        add(panel);		// Προσθήκη του panel (με όλα τα components μέσα) στο JFrame της κλάσης TruckRoutingSimulatorDB, καθώς από εδώ και πέρα, το panel ορίζεται ως κεντρικό GUI container του παραθύρου.

        // Παρακάτω γίνεται η φόρτωση των δεδομένων, όπου ο χρήστης βλέπει μόνο τους κωδικούς των routes στο drop-down, ενώ ταυτόχρονα εάν προστεθεί μία νέα διαδρομή στη βάση δεδομένων, απλώς ενημερώνεται το combo box την ώρα εκτέλεσης της εφαρμογής, χωρίς κάποια αλλαγή του GUI.
        routeDistances = RouteDAO.getRoutesWithDistances();		// Σε αυτό το σημείο καλείται η μέθοδος getRoutesWithDistances() της κλάσης RouteDAO, καθώς ανήκει στο data access layer (DAL) ή DAO layer και είναι υπεύθυνη για την πρόσβαση σε δεδομένα από την βάση. Η μέθοδος επιστρέφει ένα HashMap<String, Double>, όπου το Key (String) είναι ο κωδικός της διαδρομής (route), π.χ. "R1", "R2" και το Value (Double) είναι η απόσταση της διαδρομής σε χιλιόμετρα, ενώ το αποτέλεσμα αποθηκεύεται στη μεταβλητή routeDistances, η οποία είναι state του GUI και με αυτόν τον τρόπο μπορούμε να έχουμε πρόσβαση στις διαδρομές και τις αποστάσεις σε όλη την κλάση TruckRoutingSimulatorDB.
        routeDistances.keySet().forEach(routeCombo::addItem);	// Σε αυτό το σημείο μέσω του routeDistances.keySet() επιστρέφονται όλα τα keys του map, δηλαδή όλοι οι κωδικοί των διαδρομών(πχ. ["R1", "R2", "R3" κλπ.], καθώς μέσω του .forEach(routeCombo::addItem) για κάθε key (route code) καλείται η μέθοδος addItem του routeCombo (που είναι JComboBox), όπου το routeCombo είναι το drop-down με τις διαδρομές στο GUI, με αποτέλεσμα όλες οι διαδρομές που υπάρχουν στη βάση δεδομένων να φορτώνονται αυτόματα στο combo box και ο χρήστης να μπορεί να επιλέξει μία από αυτές τις διαδρομές.

        // Παρακάτω δημιουργούνται οι ActionListeners για τα κουμπιά.
        // Η σχεδιαστική λογική αυτή βασίζεται σε Event-driven architecture, όπου όλα τα κουμπιά και τα combo boxes “ακούν” τον χρήστη μέσω των ActionListeners, καθώς κάθε ενέργεια του χρήστη εκτελεί μόνο τη συγκεκριμένη μέθοδο που είναι υπεύθυνη για το αποτέλεσμα. Σύμφωνα με το Separation of concerns το GUI είναι υπεύθυνο μόνο για εισαγωγή/εμφάνιση δεδομένων και ενεργοποίηση των μεθόδων, από την στιγμή που το SimulationEngine κάνει τους υπολογισμούς και τις προσομοιώσεις, ενώ το GUI απλώς εμφανίζει τα αποτελέσματα. Με την κλήση loadWarehouses() και loadCustomers() κατά την εκκίνηση, ο χρήστης βλέπει αμέσως πλήρη περιβάλλον χωρίς να χρειαστεί να κάνει επιπλέον ενέργειες.
        warehouseTypeCombo.addActionListener(e -> loadWarehouses());	// Προσθήκη του ActionListener στο combo box warehouseTypeCombo, που αυτό σημαίνει ότι όταν ο χρήστης αλλάξει την επιλογή τύπου αποθήκης (π.χ. από "Short" σε "Long"), θα εκτελεστεί η συνάρτηση αυτή loadWarehouses(), ενώ ταυτόχρονα η χρήση του e -> loadWarehouses() είναι ένα lambda expression, που σημαίνει ότι δεν χρειάζεται να γράψουμε όλη την κλασική ανώνυμη κλάση new ActionListener(){...}. Πιο συγκεκριμένα η μέθοδος loadWarehouses() ανανεώνει τα στοιχεία του combo box warehouseCombo με τις αποθήκες του επιλεγμένου τύπου.
        calculateButton.addActionListener(e -> calculate6MonthProfit());	// Προσθήκη του ActionListener στο κουμπί calculateButton, όπου όταν ο χρήστης πατήσει το κουμπί, θα εκτελεστεί η μέθοδος αυτή calculate6MonthProfit(), η οποία δημιουργεί ένα αντικείμενο SimulationEngine με βάση τα επιλεγμένα στοιχεία (warehouse, route, truck) και υπολογίζει το 6μηνιαίο κέρδος και το αποτέλεσμα εμφανίζεται στο resultArea (JTextArea).
        bestRouteButton.addActionListener(e -> findBestRouteAutomatically());	// Προσθήκη του ActionListener στο κουμπί bestRouteButton, όπου όταν πατηθεί το κουμπί καλείται η μέθοδος αυτή findBestRouteAutomatically(), η οποία υπολογίζει ποια διαδρομή μεγιστοποιεί το κέρδος για τον επιλεγμένο τύπο αποθήκης και φορτηγού, και εμφανίζει τα αποτελέσματα στο resultArea.
        hourlySimButton.addActionListener(e -> runHourlySimulation());		// Προσθήκη ActionListener στο κουμπί hourlySimButton, όπου μόλις πατηθεί το κουμπί καλείται η μέθοδος runHourlySimulation(), η οποία εκτελεί απλή, deterministic προσομοίωση ανά ώρα (με σίγουρα δεδομένα ώρας προκαθορισμένα) και εμφανίζει τα αποτελέσματα στο resultArea.
        stochasticSimButton.addActionListener(e -> runStochasticSimulation());		// Προσθήκη ActionListener στο κουμπί stochasticSimButton, όπου καλείται η μέθοδος runStochasticSimulation(), η οποία εκτελεί στοχαστική, βελτιστοποιημένη προσομοίωση, όπου δημιοργούνται τυχαίες ώρες παράδοσης.
        allRoutesProfitButton.addActionListener(e -> calculateProfitForAllRoutes());	// Προσθήκη ActionListener στο κουμπί allRoutesProfitButton, όπου όταν πατηθεί, καλείται η μέθοδος calculateProfitForAllRoutes(), η οποία υπολογίζει το 6μηνιαίο κέρδος για όλες τις διαδρομές και εμφανίζει τα αποτελέσματα στο resultArea.

        // Οι 2 αυτές μέθοδοι παρακάτω εκτελούνται αμέσως κατά την εκκίνηση του GUI. Στην ουσία όταν εμφανιστεί το GUI στην οθόνη του χρήστη, τα combo boxes είναι ήδη πλήρως γεμάτα με τα στοιχεία που χρειάζεται ο χρήστης για να ξεκινήσει τις προσομοιώσεις.
        loadWarehouses();	// Γεμίζει το combo box με τις αποθήκες του αρχικά επιλεγμένου τύπου.
        loadCustomers();	// Γεμίζει το combo box customerCombo με όλους τους πελάτες από τη βάση δεδομένων.
    
    }	// Εδώ κλείνει ο κατασκευαστής.

    // Η loadWarehouses() είναι μέθοδος συγχρονισμού GUI με τα δεδομένα. Φροντίζει ώστε το combo box των αποθηκών (warehouseCombo) να δείχνει μόνο τις αποθήκες που αντιστοιχούν στον επιλεγμένο τύπο αποθήκης (Short ή Long), καθώς καλείται όταν αλλάζει το warehouseTypeCombo κατά την αρχικοποίηση του GUI.
    private void loadWarehouses() {		// Η μέθοδος αυτή είναι ορατή και προσβάσιμη μόνο μέσα στο GUI και το ενημερώνει ανάλογα, όπου στην ουσία είναι μία βοηθητική μέθοδος, η οποία φορτώνει τις αποθήκες.
        warehouseCombo.removeAllItems();	// Σε αυτό το σημείο αφαιρούνται όλες οι προηγούμενες επιλογές από το warehouseCombo, όπου στην ουσία αν ο χρήστης αλλάξει από Short σε Long, δεν πρέπει να μείνουν οι παλιοί τύποι αποθηκών, καθώς κάθε αλλαγή φίλτρου κάνει ένα reset των δεδομένων ενημερώνοντας σωστά το UI.
        String type = ((String) warehouseTypeCombo.getSelectedItem()).toUpperCase();	// Εδώ διαβάζεται ο τύπος αποθήκης, όπου μέσω του warehouseTypeCombo.getSelectedItem() επιστρέφεται το αντικείμενο που επέλεξε ο χρήστης ("Short" ή "Long") και γίνεται ένα typecast σε String, διότι το Combobox περιέχει ένα αντικείμενο.
        List<String> warehouses = WarehouseDAO.getWarehousesByType(type);	// Ανάκτηση δεδομένων από το WarehouseDAO, όπου καλείται η WarehouseDAO.getWarehousesByType(type) κι επιστρέφεται List<String> με τα ονόματα των αποθηκών, από την στιγμή που το GUI ζητά την ανάκτηση αποθηκών ενός συγκεκριμένου τύπου.
        warehouses.forEach(warehouseCombo::addItem);		// Μέσω του forEach(...) διατρέχονται όλα τα στοιχεία της λίστας και για κάθε στοιχείο της λίστας γεμίζει δυναμικά από την βάση δεδομένων το dropdown με τις σωστές αποθήκες. Στην ουσία εδώ σε κάθε επανάληψη και για κάθε αποθήκη που επέστρεψε το DAO, προστίθεται ως επιλογή στο combo box του GUI μέσω της μεθόδου addItem.
    }	// ολοκλήρωση μεθόδου με καθαρισμό του combo box, διάβασμα του τύπου αποθήκης, ανάκτηση σωστών δεδομένων και ενημέρωση του UI.

    // Η μέθοδος αυτή καθαρίζει και ενημερώνει το dropdown πελατών, ζητά όλους τους πελάτες από το DAO και τους εμφανίζει στο GUI με φιλική μορφή προς τον χρήστη εξ ου που είναι απαραίτητη η μετατροπή σε String για την αποθήκευση μέσα στο JComboBox, καθώς πρόκειται για μία βοηθητική μέθοδο, η οποία δεν περιέχει business logic και δεν κάνει υπολογισμούς.
    private void loadCustomers() {		// Η μέθοδος αυτή χρησιμοποιείται μόνο εντός αυτής της κλάσης, όπου φορτώνει όλους τους customers(=πελάτες) στο GUI γεμίζοντας το customerCombo, δηλαδή το drop-down list.
        customerCombo.removeAllItems();		// Σε αυτό το σημείο μέσω της μεθόδου removeAllItems(), αδειάζουν όλες οι υπάρχουσες επιλογές του JComboBox, με αποτέλεσμα κάθε φορά που θα καλείται η μέθοδος loadCustomers() δεν θα υπάρχουν πλέον διπλότυπες εγγραφές πελατών, καθώς το GUI δεν κρατά cache δεδομένων, με αποτέλεσμα να προχωρά σε εκκαθάριση των δεδομένων και χτύσιμο ξανά των δεδομένων από την υπεύθυνη κλάση CustomerDAO. Πιο συγκεκριμένα το customerCombo ήταν ήδη ένα αντικείμενο JComboBox<String>, το οποίο περιείχε παλιούς πελάτες μέσα και δεδομένα από προηγούμενη επιλογή του χρήστη, καθώς μέσω αυτής της εντολής σβήνονται όλα και το dropdown αδειάζει, καθώς αυτό έχει ως αποτέλεσμα να μην εμφανιστούν διπλότυπα και κάθε φορά φορτώνονται ξανά τα δεδομένα από την DAO κλάση.
        List<CustomerDAO.Customer> customers = CustomerDAO.getAllCustomers();		// Σε αυτό το σημείο καλείται το Data Access Layer, όπου μέσω του CustomerDAO.getAllCustomers() πραγματοποιείται η ανάγνωση δεδομένων από την βάση και επιστρέφεται η List<Customer>, για ανάκτηση των πελατών από την βάση.
        customers.forEach(c -> customerCombo.addItem(c.name + " (" + c.city + ")"));		// Μέσω του forEach και σε κάθε επανάληψη για κάθε πελάτη (Customer), μετατρέπεται το αντικείμενο Customer σε String, προκειμένου να αποθηκευθεί μέσα στο JComboBox εμφανίζοντας το όνομα του πελάτη και την πόλη στην οποία βρίσκεται ο πελάτης. Πιο συγκεκριμένα για κάθε Customer c στη λίστα ανακτάται το c.name(όνομα πελάτη) και το c.city(πόλη πελάτη) δημιουργώντας ένα String, το οποίο προστίθεται ως επιλογή στο JComboBox, όπου με αυτόν τον τρόπο γίνεται η μετατροπή σε String, καθώς μέσα στο JComboBox<String> δεν αποθηκεύονται αντικείμενα Customer αλλά μόνο Strings για καλύτερη αναγνωσιμότητα από τον χρήστη κι έτσι εμφανίζονται τα δεδομένα.
    }

    private TruckDAO.Truck getSelectedTruck() {		// Δημιουργία της βοηθητικής αυτής μεθόδου που χρησιμοποιείται μόνο εντός της κλάσης, η οποία μεταφράζει την GUI επιλογή σε ένα domain object, βάσει της αρχής του Encapsulation επιστρέφοντας ένα TruckDAO.Truck, ενώ το GUI αναλαμβάνει το mapping View → Model.
        String selectedTruck = (String) truckCombo.getSelectedItem();		// Μέσω της μεθόδου getSelectedItem(), αφού το String έχει ήδη δημιουργηθεί κατά το γέμισμα του ComboBox, διαβάζεται το ήδη παρουσιασμένο String. Στην ουσία το JComboBox<String> αποθηκεύει μόνο Strings, ενώ το getSelectedItem() επιστρέφει το Object, το οποίο γίνεται explicit cast σε String.
        if (selectedTruck == null) return null;		// Σε αυτό το σημείο πραγματοποιείται ένας έλεγχος για null, όπου εάν ο χρήστης δεν επέλεξε τίποτα ή το ComboBox δεν έχει φορτωθεί, τότε η getSelectedItem() επιστρέφει null, που σημαίνει ότι δεν καταρρέει όλη η εφαρμογή λόγω του NullPointerException. Στην ουσία προστατεύεται η εφαρμογή από μη επιλεγμένο στοιχείο, μη φορτωμένο combo και ασύγχρονο GUI state, από την στιγμή που το GUI φιλτράρει invalid user state καθώς το SimulationEngine δεν θα πρέπει να ασχολείται με null GUI inputs.
        List<TruckDAO.Truck> trucks = TruckDAO.getAllTruckTypes();		// Σε αυτό το σημείο καλείται το Data Access Layer, όπου επιστρέφονται όλα τα διαθέσιμα φορτηγά από τη βάση, από την στιγμή που το GUI δεν κρατά cache domain objects και κάθε φορά ζητά εκ νέου την ανάκτηση δεδομένων. Πιο συγκεκριμένα το GUI δεν αποθηκεύει domain state, καθώς λειτουργεί ως stateless view, επιτρέποντας την δυνατότητα μελλοντικής αλλαγής στην βάση.
        return trucks.stream().filter(t -> selectedTruck.startsWith(t.getCategory())).findFirst().orElse(null);		// Σε αυτό το σημείο μετατρέπεται η λίστα (List<Truck>) σε Stream (Stream<Truck>), όπου μέσω του φίλτρου αυτού .filter(t -> selectedTruck.startsWith(t.getCategory())) πραγματοποιείται η αντιστοίχηση φορτηγού, όπου το t.getCategory() περιέχει την κατηγορία του φορτηγού και το startsWith τίθεται με την τιμή true, που σημαίνει ότι το GUI περιέχει πληροφορίες προς παρουσίαση από την στιγμή που το domain object(Truck) περιέχει μόνο την κατηγορία. Μέσω χρήσης του .findFirst() λαμβάνεται το πρώτο Truck που ταιριάζει κι επιστρέφεται το Optional<Truck> από την στιγμή που κάθε κατηγορία φορτηγού είναι μοναδική στο σύστημα. Στην συνέχεια μέσω χρήσης του .orElse(null) αν δεν βρεθεί αντιστοίχιση επιστρέφεται η τιμή null, καθώς το SimulationEngine θα κληθεί μόνο αν το Truck δεν είναι null.
    }

    // Η μέθοδος calculate6MonthProfit() είναι controller-μέθοδος για το GUI, η οποία παίρνει επιλογές χρήστη από το UI → τις μετατρέπει σε domain δεδομένα → καλεί το SimulationEngine → εμφανίζει το αποτέλεσμα, καθώς δεν περιέχει καμία επιχειρησιακή λογική, καθώς είναι GUI event handler και καλείται μόνο από τον ActionListener, ενημερώνοντας αντίστοιχα το UI από την στιγμή που η μέθοδος είναι Controller logic (MVC).
    private void calculate6MonthProfit() {		// Η μέθοδος αυτή χρησιμοποιείται μόνο από τον event listener, καθώς δεν αποτελεί API και δεν ανήκει στο business layer, διότι η controller αυτή μέθοδος παράγει μόνο τα side effects στο View προβάλλοντας στο UI (resultArea) τα αποτελέσματα. Η μέθοδος αυτή είναι προσβάσιμη μόνο εντός της κλάσης (private), όπου πρόκειται για μία controller μέθοδο στο πλαίσιο του MVC (Model-View-Controller), όπου παίρνει τα δεδομένα από το UI (View), τα μετατρέπει σε domain objects (Model) και καλεί την επιχειρησιακή λογική (SimulationEngine).
        String warehouseType = ((String) warehouseTypeCombo.getSelectedItem()).toUpperCase();	// Μέσω του getSelectedItem() επιστρέφεται ένα Object, γίνεται cast σε String και περιέχει τις τιμές "Short" ή "Long", καθώς λαμβάνεται η επιλεγμένη τιμή από το JComboBox (warehouseTypeCombo), αφού το warehouseType χρησιμοποιείται για φιλτράρισμα πελατών και υπολογισμούς στο SimulationEngine. 
        String routeCode = (String) routeCombo.getSelectedItem();		// Σε αυτό το σημείο λαμβάνεται η επιλεγμένη διαδρομή από το JComboBox (routeCombo). 
        if (routeCode == null) return;		// Εδώ ελέγχεται η απουσία επιλογής (τιμή null), τερματίζοντας την μέθοδο με return, δίχως την ολική κατάρευση της εφαρμογής, προστατεύοντας με από NullPointerException αν ο χρήστης δεν έχει επιλέξει κάποιο route.

        TruckDAO.Truck selectedTruck = getSelectedTruck();		// Σε αυτό το σημείο καλείται η βοηθητική μέθοδος getSelectedTruck() για να πάρει το domain object TruckDAO.Truck που αντιστοιχεί στην επιλογή του χρήστη.
        if (selectedTruck == null) return;		// Εδώ ελέγχεται εάν δεν υπάρχει φορτηγό και η τιμή είναι null με αποτέλεσμα να σταματάει η εκτέλεση της μεθόδου, καθώς το GUI δεν δουλεύει απευθείας με Strings, αλλά με domain objects, ώστε να διατηρείται ενεργός ο διαχωρισμός View ↔ Model.

        List<CustomerDAO.Customer> customers = CustomerDAO.getAllCustomers().stream()		// Σε αυτό το σημείο λαμβάνονται όλοι οι πελάτες από το DAO layer (CustomerDAO.getAllCustomers()), όπου χρησιμοποιεί Streams + filter για να κρατήσει μόνο τους πελάτες που ανήκουν στον επιλεγμένο τύπο αποθήκης (warehouseType), ενώ μέσω της μεθόδου equalsIgnoreCase() επιτρέπεται η case-insensitive σύγκριση, καθώς μέσω του collect(Collectors.toList()) μετατρέπεται το Stream ξανά σε List<Customer>. Στην ουσία δημιουργείται η λίστα domain objects που χρειάζεται η SimulationEngine.
                .filter(c -> c.warehouseType.equalsIgnoreCase(warehouseType))
                .collect(Collectors.toList());

        SimulationEngine engine = new SimulationEngine(customers, routeDistances, selectedTruck, warehouseType);	// Εδώ δημιουργείται ένα αντικείμενο SimulationEngine, το οποίο μεταβιβάζει τους customers → πελάτες για τη συγκεκριμένη αποθήκη, τις routeDistances → χάρτη routeCode → απόσταση, το selectedTruck → φορτηγό που θα χρησιμοποιηθεί στους υπολογισμούς, το warehouseType → τύπο αποθήκης για πιθανή διαφοροποίηση λογικής, καθώς το GUI δεν κάνει υπολογισμούς, διότι απλά περνάει τα δεδομένα στο business layer (SimulationEngine).
        double totalProfit = engine.calculateProfitForRoute(routeCode);		// Σε αυτό το σημείο καλείται η μέθοδος του SimulationEngine για να υπολογίσει το 6μηνιαίο κέρδος για τη συγκεκριμένη διαδρομή, από την στιγμή που η μεταβλητή totalProfit είναι τύπου double και κρατάει το αποτέλεσμα αυτό, με αποτέλεσμα να υπάρχει πλήρης διαχωρισμός, καθώς το GUI δεν γνωρίζει τους αλγορίθμους υπολογισμού, από την στιγμή που εμφανίζει μόνο το αποτέλεσμα.

        resultArea.setText("");		// Καθαρισμός του JTextArea πριν εμφανιστούν νέα αποτελέσματα, διασφαλίζοντας ότι δεν μένουν προηγούμενα αποτελέσματα στην οθόνη.
        // Οι παρακάτω γραμμές κώδικα παίρνουν τιμές από μεταβλητές (warehouseType, routeCode, selectedTruck, totalProfit) και τις εμφανίζουν στο GUI με μορφοποίηση και ενημερωτικά κείμενα, όπου ότι επιλογές έχει κάνει ο χρήστης εμφανίζει στην οθόνη του βάσει των επιλογών που έκανε, το κέρδος που υπολογίστηκε.
        resultArea.append("📦 MANUAL ROUTE SELECTION\n\n");		// Προσθήκη κειμένου μέσω της μεθόδου append(), για χειροκίνητη επιλογή διαδρομής από τον χρήστη με 2 αλαγές γραμμής.
        resultArea.append("Warehouse Type: " + warehouseType + "\n");		// Προσθήκη τύπου αποθήκης που επέλεξε ο χρήστης για εμφάνιση στην οθόνη (πχ. Warehouse Type: SHORT).
        resultArea.append("Route: " + routeCode + "\n");		// Προσθήκη διαδρομής που επέλεξε ο χρήστης για εμφάνιση στην οθόνη (πχ. Route: R1).
        resultArea.append("Truck Type: " + selectedTruck.getCategory() + "\n");		// Προσθήκη του τύπου φορτηγού που επέλεξε ο χρήστης, όπου μέσω της selectedTruck.getCategory() επιστρέφεται (π.χ. "Medium" ή "Large") εμφανίζοντας για παράδειγμα στην οθόνη (Truck Type: Medium).
        resultArea.append("Total Profit (6 months): " + String.format("%.2f", totalProfit) + " €\n");	  // Προσθήκη γραμμής για το υπολογιζόμενο κέρδος των 6 μηνών, όπου μέσω του String.format("%.2f", totalProfit), μορφοποιείται το αποτέλεσμα σε 2 δεκαδικά ψηφία (π.χ. 1234.56).
    }

    // Η μέθοδος (findBestRouteAutomatically), είναι μία controller μέθοδος σύμφωνα με το MVC pattern, όπου καλείται αποκλειστικά από τον ActionListener (κουμπί στο GUI-event driven), καθώς εμφανίζει τα αποτελέσματα στην οθόνη του χρήστη μέσω των επιλογών που έχει κάνει ο χρήστης στο (resultArea, routeCombo), αφού αυτή η μέθοδος δεν είναι business logic και δεν περιέχει υπολογισμούς κόστους ή εσόδων και δεν κάνει προσπέλαση στην βάση δεδομένων απευθείας.
    // Στην ουσία μέσω της παρακάτω μεθόδου υλοποιείται ένας γραμμικός αλγόριθμος βελτιστοποίησης, ο οποίος αξιολογεί εξαντλητικά όλα τα διαθέσιμα routes μέσω του SimulationEngine, διατηρεί το μέγιστο κέρδος και το αντίστοιχο route, και στη συνέχεια ενημερώνει το GUI, ακολουθώντας πλήρως τις αρχές MVC και Single Responsibility.
    private void findBestRouteAutomatically() {		// Η μέθοδος αυτή είναι controller μέθοδος (MVC) που παίρνει τις επιλογές του χρήστη από το GUI → δοκιμάζει ΟΛΑ τα routes → βρίσκει ποιο route δίνει το μεγαλύτερο κέρδος για 6 μήνες και τέλος ενημερώνει το UI.
        String warehouseType = ((String) warehouseTypeCombo.getSelectedItem()).toUpperCase();	// Σε αυτό το σημείο λαμβάνεται η επιλογή από την drop-down list που έκανε ο χρήστης (short ή long type warehouse), όπου το warehouseTypeCombo είναι JComboBox<String> και μέσω της getSelectedItem() επιστρέφει ένα Object και γίνεται explicit cast σε String για να μπορέσει να αποθηκευθεί μέσα στο JComboBox, καθώς δεν είναι εφικτή η αποθήκευση αντικειμένων μέσα στο JComboBox, από την στιγμή που το GUI δεν κρατά domain objects και κρατά μόνο strings για παρουσίαση.
        TruckDAO.Truck selectedTruck = getSelectedTruck();		// Εδώ ανακτάται το φορτηγό που επέλεξε ο χρήστης, καθώς εάν δεν έχει επιλεγεί φορτηγό (δηλαδή έχει την τιμή null), τότε τερματίζεται η μέθοδος με την εντολή (return), σύμφωνα με τον αμυντικό προγραμματισμό (Defensive programming για αποφυγή NullPointerException). Σε αυτό το σημείο διαβάζεται το επιλεγμένο φορτηγό από το UI και μετατρέπει το string της επιλογής σε πραγματικό domain object (Truck), από την στιγμή που το SimulationEngine χρειάζεται χωρητικότητα φορτηγού, κατηγορία φορτηγού και το πλήθος (τον αριθμό) των φορτηγών. 
        if (selectedTruck == null) return;		// Τερματισμός της μεθόδου στην περίπτωση που ο χρήστης δεν έχει επιλέξει φορτηγό για να αποφευχθεί η πλήρης κατάρρευση της εφαρμογής και να αποφευχθεί το NullPointerException.

        List<CustomerDAO.Customer> customers = CustomerDAO.getAllCustomers().stream()	// Εδώ γίνεται ανάκτηση όλων των πελατών από την βάση δεδομένων, όπου μέσω του .filter(c -> c.warehouseType.equalsIgnoreCase(warehouseType)) φιλτράρονται και διατηρούνται μόνο όσοι πελάτες εξυπηρετούνται από τον επιλεγμένο τύπο αποθήκης, με αποτέλεσμα να δημιουργείται μία νέα immutable λίστα από την στιγμή που το GUI καθορίζει την προσωμοίωση. 
                .filter(c -> c.warehouseType.equalsIgnoreCase(warehouseType))
                .collect(Collectors.toList());

        SimulationEngine engine = new SimulationEngine(customers, routeDistances, selectedTruck, warehouseType);	// Σύμφωνα με το MVC pattern, το GUI δεν κάνει κανέναν υπολογισμό, καθώς παραδίδει μόνο (πελάτες, routes, φορτηγό, warehouse type) στο SimulationEngine, προκειμένου να αναλάβει αυτή η κλάση να κάνει τους απαραίτητους υπολογισμούς, σύμφωνα με τις παραδόσεις του GUI προς το SimulationEngine, καθώς πραγματοποιείται Dependency Injection μέσω του constructor.

        // Στην ουσία οι μεταβλητές bestProfit και bestRoute υλοποιούν έναν αλγόριθμο εύρεσης μέγιστου, όπου το Double.NEGATIVE_INFINITY λειτουργεί ως ουδέτερο αρχικό όριο σύγκρισης, επιτρέποντας την ορθή αξιολόγηση όλων των πιθανών διαδρομών, ακόμη και σε περιπτώσεις αρνητικού κέρδους.
        double bestProfit = Double.NEGATIVE_INFINITY;	// Σε αυτό το σημείο δημιουργείται μία μεταβλητή που θα κρατά το καλύτερο (μέγιστο) κέρδος που έχει βρεθεί μέχρι τη δεδομένη στιγμή, καθώς μέσω του NEGATIVE_INFINITY λαμβάνονται ακόμα και τα κέρδη εκείνα που μπορεί να είναι αρνητικά, όπου μέσω του Double.NEGATIVE_INFINITY αναπαρίσταται ο μικρότερος από οποιονδήποτε πραγματικό αριθμό, διότι αν χρησιμοποιούσαμε το 0 (ως τιμή εκχώρησης μέσα στην μεταβλητή), τότε ένα route με -500 € δεν θα γινόταν ποτέ αποδεκτό κι άρα θα παίρναμε λάθος αποτέλεσμα. Στην ουσία για κάθε πραγματικό αριθμό το πρώτο route που θα ελεγχθεί πάντα θα αντικαθιστά το αρχικό bestProfit.
        String bestRoute = null;	// Το bestProfit αναπαριστά το ποσό και το bestRoute αναπαριστά την διαδρομή εκείνη με το μεγαλύτερο κέρδος, καθώς η μεταβλητή αυτή αρχικοποιείται με null, διότι προτού ξεκινήσει ο έλεγχος δεν υπάρχει ακόμα καλύτερο route, καθώς το null υποδηλώνει ότι δεν έχει επιλεγεί τίποτα ακόμα.

        for (String routeCode : routeDistances.keySet()) {		// Μέσω της enhanced for σε κάθε επανάληψη διατρέχονται όλα τα διαθέσιμα routes, όπου το routeDistances είναι Map<String, Double>, καθώς το key είναι το routeCode (π.χ. "R1", "ATH-THES") και το value είναι η απόσταση του route, ενώ μέσω του keySet() επιστρέφονται όλοι οι κωδικοί διαδρομών. Το GUI δεν ξέρει τίποτα για τις αποστάσεις, τα κόστη κι απλά δοκιμάζει το κάθε route. 
            double profit = engine.calculateProfitForRoute(routeCode);	// Για κάθε route σε κάθε επανάληψη υπολογίζεται το συνολικό κέρδος 6 μηνών, καθώς το SimulationEngine αποφασίζει για την χωρητικότητα, τα trips, την απόσταση, τα έσοδα και το κόστος, σύμφωνα με την αρχή του Single Responsibility Principle (=>SimulationEngine κάνει μόνο τους υπολογισμούς και το GUI έχει μόνη ευθύνη το interaction με τον χρήστη), καθώς το GUI δεν υλοποιεί τους υπολογισμούς και δεν γνωρίζει πολιτικές τιμολόγησης.
            if (profit > bestProfit) {		// Σε αυτό το σημείο εάν το τρέχον route αποφέρει μεγαλύτερο κέρδος από το προηγούμενο καλύτερο τότε πραγματοποιείται η αποθήκευση αυτού του καλύτερου route, δηλαδή αποθηκεύεται ο κωδικός της διαδρομής (route), που βρέθηκε ότι είναι η καλύτερη διαδρομή καθώς και το κέρδος που αντιστοιχεί σε αυτό το route. Στην ουσία μετά από κάθε επανάληψη, το bestProfit περιέχει το μέγιστο κέρδος από όλα τα routes που έχουν ελεγχθεί.
                bestProfit = profit;		// Εδώ αποθηκεύεται το νέο μέγιστο κέρδος 6 μηνών.
                bestRoute = routeCode;		// Εδώ αποθηκεύεται το route που πέτυχε το νέο μέγιστο κέρδος 6 μηνών.
            }
        }

        routeCombo.setSelectedItem(bestRoute);	// Το route που βρέθηκε ως το καλύτερο route επιλέγεται αυτόματα από την drop-down list, για ενημέρωση του UI (συγχρονισμός του View με το αποτέλεσμα).
        resultArea.setText("");		// Εκκαθάριση του αποτελέσματος για αποφυγή παλιών δεδομένων.
        resultArea.append("🚀 AUTOMATIC ROUTE OPTIMIZATION\n\n");	// Εδώ ενημερώνεται ο χρήστης ότι έχει επιλεγεί η αυτόματη επιλογή route, που σηματοδοτεί την εμφάνιση του βέλτιστου δρομολογίου με αυτόματη απόφαση από το σύστημα με τα δεδομένα για τα οποία έγινε η βελτιστοποίηση.
        resultArea.append("Warehouse Type: " + warehouseType + "\n");	// Παρουσιάζει στον χρήστη ότι επιλέχθηκε ο τύπος αποθήκης.
        resultArea.append("Truck Type: " + selectedTruck.getCategory() + "\n");		// Παρουσιάζει ότι επιλέχθηκε η συγκεκριμένη κατηγορία φορτηγού.
        resultArea.append("Best Route: " + bestRoute + "\n");		// Παρουσιάζει την επιλογή route που έκανε ο χρήστης.
        resultArea.append("Maximum Profit (6 months): " + String.format("%.2f", bestProfit) + " €\n");		// Κι εδώ παρουσιάζεται στον χρήστη το τελικό αποτέλεσμα με ακρίβεια 2 δεκαδικών ψηφίων.
    }

    // Οι 2 μέθοδοι runHourlySimulation() και runStochasticSimulation() καλούν την μέθοδο private void runHourlySimulation(boolean stochastic), η οποία διαβάζει δεδομένα από το UI, δημιουργεί το SimulationEngine, επιλέγει ποια προσομοίωση θα τρέξει (deterministic ή stochastic simulation), επιστρέφει ένα String κι ενημερώνει αντίστοιχα με τα αποτελέσματα το ResultArea. Στην ουσία πατώντας τα κουμπιά “Run Hourly Simulation” και “Run Stochastic Simulation” εκτελείται η αντίστοιχη προσομοίωση και καλούνται οι αντίστοιχες μέθοδοι για εκτέλεση του κώδικα. Ο Controller runHourlySimulation() runStochasticSimulation() και runHourlySimulation(boolean stochastic) αποφασίζει ποιο από τα simulations θα υλοποιηθεί. Στην ουσία οι μέθοδοι runHourlySimulation() και runStochasticSimulation() λειτουργούν ως σημασιολογικά σημεία εισόδου του controller, μεταφράζοντας τις ενέργειες του χρήστη σε συγκεκριμένες παραμέτρους εκτέλεσης της ίδιας υποκείμενης λειτουργίας, επιτυγχάνοντας καθαρό διαχωρισμό ευθυνών και αποφυγή διπλού κώδικα.
    // Στην ουσία παρακάτω δεν “τρέχουν” δύο διαφορετικές προσομοιώσεις, αλλά τρέχει μία προσομοίωση με δύο διαφορετικά modes, και ο controller απλώς μεταφράζει το τι ζήτησε ο χρήστης.
    private void runHourlySimulation() {	// Η συγκεκριμένη μέθοδος είναι μία controller μέθοδος, η οποία καλείται από το κουμπί του GUI hourlySimButton.addActionListener(e -> runHourlySimulation()), καλώντας εσωτερικά την μέθοδο runHourlySimulation (που περιέχει τις δοθείσες ώρες που τρέχει το simulaton). Ο χρήστης πατάει το κουμπί hourlySimButton.addActionListener(e -> runHourlySimulation()) και ο ActionListener καλεί την μέθοδο αυτή runHourlySimulation(), η οποία μεταφράζει το UI event σε εκτέελση του simulation με προκαθορισμένες ώρες.
        runHourlySimulation(false);
    }

    private void runStochasticSimulation() {	// Η μέθοδος αυτή καλείται να εκτελεστεί πατώντας το κουμπί stochasticSimButton.addActionListener(e -> runStochasticSimulation()), που τρέχει την αντίστοιχη προσωμοίωση με τυχαίες ώρες. Ο χρήστης πατάει το κουμπί stochasticSimButton.addActionListener(e -> runStochasticSimulation()) και καλείται η μέθοδος αυτή runStochasticSimulation(), η οποία τρέχει simulation με τυχαίες ώρες.
        runHourlySimulation(true);
    }

    // Η μέθοδος runHourlySimulation(boolean stochastic) λειτουργεί ως ενοποιημένος controller μηχανισμός εκτέλεσης προσομοιώσεων, μεταφράζοντας τις επιλογές του χρήστη σε domain δεδομένα, επιλέγοντας τον κατάλληλο τύπο προσομοίωσης και ενημερώνοντας το UI με το παραγόμενο αποτέλεσμα, χωρίς να εμπλέκεται σε καμία επιχειρησιακή λογική.
    private void runHourlySimulation(boolean stochastic) {		// Η μέθοδος runHourlySimulation(boolean stochastic) είναι controller μέθοδος (MVC) που διαβάζει την κατάσταση του UI → μετατρέπει τις επιλογές σε domain δεδομένα → καλεί το κατάλληλο simulation στο business layer και τέλος εμφανίζει το αποτέλεσμα στο UI, δίχως να περιέχει κάποια επιχειρησιακή λογική. Είναι private που σημαίνει ότι καλείται μόνο εσωτερικά από τον controller, με παράμετρο εισόδου μία boolean τιμή, η οποία αν είναι false → deterministic hourly simulation ενώ αν είναι true → stochastic optimized simulation, καθώς η μέθοδος αυτή δεν γνωρίζει ποιο κουμπί πάτησε ο χρήστης, αφού ξέρει μόνο ποιο mode πρέπει να εκτελέσει.
        String warehouseType = ((String) warehouseTypeCombo.getSelectedItem()).toUpperCase();	// Σε αυτό το σημείο διαβάζεται η επιλογή του χρήστη από το JComboBox, μέσω του getSelectedItem() επιστρέφεται ένα Object, γίνεται explicit cast σε String.
        TruckDAO.Truck selectedTruck = getSelectedTruck();	// Σε αυτό το σημείο γίνεται ανάκτηση του επιλεγμένου φορτηγού, όπου η getSelectedTruck() διαβάζει το String και το αντιστοιχίζει σε πραγματικό Truck object, το οποίο το χρειάζεται η SimulationEngine, από την στιγμή που μετατρέπεται το View σε Model.
        if (selectedTruck == null) return;		// Στην περίπτωση που δεν έχει επιλεγεί φορηγό, δεν υπάρχει χωρητικότητα, δεν υπάρχει αριθμός φορτηγών (άρα το selectedTruck είναι null), τότε τερματίζεται η μέθοδος με αποτέλεσμα να προλαμβάνεται η περίπτωση κατάρρευσης όλης της εφαρμογής λόγω του NullPointerException.

        List<CustomerDAO.Customer> customers = CustomerDAO.getAllCustomers().stream()	// Σε αυτό το σημείο ανακτώνται όλοι οι πελάτες από το DAO, δημιουργείται ένα Stream, καθώς φιλτράρονται οι πελάτες και μόνο όσοι εξυπηρετούνται από τον επιλεγμένο τύπο αποθήκης, δημιουργώντας μία νέα immutable λίστα.
                .filter(c -> c.warehouseType.equalsIgnoreCase(warehouseType))
                .collect(Collectors.toList());

        SimulationEngine engine = new SimulationEngine(customers, routeDistances, selectedTruck, warehouseType);	// Ο Controller παραδίδει στο SimulationEngine τους πελάτες, τα routes, τις αποστάσεις των δρομολογίων, το φορτηγό και τον τύπο αποθήκης.

        String result;		// Δημιουργείται μια μεταβλητή τύπου String που θα κρατήσει τα αποτελέσματα της προσομοίωσης, καθώς αρχικά η default τιμή της μεταβλητής είναι null και θα ανατεθεί τιμή ανάλογα με το είδος της προσομοίωσης.
        if (stochastic) {	// Εάν ο χρήστης έχει πατήσει το κουμπί για να τρέξει η Stochastic Simulation, τότε τρέχει και εκτελείται η αντίστοιχη προσωμοίωση. Στην ουσία εδώ χρησιμοποιούμε την boolean παράμετρο stochastic, η οποία δείχνει τι θέλει ο χρήστης, όπου εάν η τιμή της μεταβήτής stochastic == true, τότε καλείται η μέθοδος engine.runStochasticSimulationToString(), που σημαίνει ότι η προσομοίωση εκτελείται με τυχαίες ώρες δρομολογίων για τους πελάτες (randomized trips), καθώς το αποτέλεσμα είναι ένα String που περιγράφει πόσα trips έγιναν, τα έσοδα, το κόστος και το κέρδος για 6 μήνες.
            result = engine.runStochasticSimulationToString();
        } else {
            result = engine.runDeterministicSimulationToString();	// Στην περίπτωση που ο χρήστης έχει πατήσει το κουμπί για να τρέξει η Deterministic Simulation, τότε τρέχει κι εκτελείται η αντίστοιχη προσομοίωση. Εάν η τιμή της μεταβλητής είναι stochastic == false, τότε καλείται η μέθοδος engine.runDeterministicSimulationToString(), όπου εκτελείται η προσωμοίωση με σταθερές, προκαθορισμένες ώρες δρομολογίων παράγοντας επίσης ένα String με τα ίδια είδη αποτελεσμάτων (trips, revenue, cost, profit). Σε κάθε περίπτωση, το result θα περιέχει το πλήρες report της προσομοίωσης σε μορφή κειμένου.
        }
        resultArea.setText(result);		// Προβολή του αποτελέσματος στο UI αντικαθιστώντας το παλιό περιεχόμενο, όπου στην ουσία το Model (παρήγαγε το αποτέλεσμα), ο Controller (παρέδωσε το αποτέλεσμα ) και το View (εμφάνισε το αποτέλεσμα). Το resultArea είναι το UI στοιχείο (π.χ., JTextArea) που δείχνει τα αποτελέσματα στον χρήστη, όπου η μέθοδος setText(result), αντικαθιστά το προηγούμενο περιεχόμενο του resultArea και εμφανίζει το νέο report που παρήχθη από το SimulationEngine. Στην ουσία εδώ ολοκληρώνεται ο κύκλος MVC, όπου το Model → SimulationEngine παρήγαγε τα δεδομένα, ο Controller → αυτή η μέθοδος αποφάσισε ποια προσομοίωση να τρέξει και πήρε το αποτέλεσμα και το View → resultArea εμφανίζει το αποτέλεσμα στον χρήστη.
    }
    
    private void calculateProfitForAllRoutes() {	// Η μέθοδος αυτή παίρνει την επιλογή του χρήστη (warehouse + truck), φιλτράρει τους πελάτες σύμφωνα με τον τύπο αποθήκης, δημιουργεί το SimulationEngine με όλα τα δεδομένα, υπολογίζει το προβλεπόμενο κέρδος για όλα τα routes για 6 μήνες και προβάλλει τα αποτελέσματα στο UI σε μορφή αναλυτικού report.
        String warehouseType = ((String) warehouseTypeCombo.getSelectedItem()).toUpperCase();	// Το warehouseTypeCombo είναι ένα ComboBox στο GUI όπου ο χρήστης επιλέγει τον τύπο αποθήκης ("SHORT" ή "LONG"), καθώς μέσω του getSelectedItem() επιστρέφεται το επιλεγμένο στοιχείο, εδώ ως Object, γι’ αυτό γίνεται cast σε String, καθώς μέσω της μεθόδου toUpperCase() μετατρέπεται το string σε κεφαλαία, ώστε να είναι συμβατό με τα δεδομένα στον DAO (αποθήκες πελατών είναι αποθηκευμένες σε κεφαλαία), ενώ αυτό το string "SHORT" ή "LONG" θα χρησιμοποιηθεί για φιλτράρισμα πελατών.
        TruckDAO.Truck selectedTruck = getSelectedTruck();		// Η μέθοδος getSelectedTruck() επιστρέφει το φορτηγό που ο χρήστης έχει επιλέξει στο UI, καθώς αν δεν υπάρχει επιλογή (null), η μέθοδος σταματάει (return) για να αποφευχθούν λάθη, καθώς δεν μπορούμε να υπολογίσουμε κέρδος χωρίς φορτηγό.
        if (selectedTruck == null) return;

        List<CustomerDAO.Customer> customers = CustomerDAO.getAllCustomers().stream()	// Το CustomerDAO.getAllCustomers() επιστρέφει όλους τους πελάτες της βάσης δεδομένων, ενώ μέσω του .stream() δημιουργείται Stream API pipeline για φιλτράρισμα, καθώς το .filter(c -> c.warehouseType.equalsIgnoreCase(warehouseType)) κρατάει μόνο τους πελάτες που αντιστοιχούν στον τύπο αποθήκης που επέλεξε ο χρήστης και τέλος μέσω του .collect(Collectors.toList()) μετατρέπεται το αποτέλεσμα σε λίστα πελατών (List<Customer>), δηλαδή ανακτώνται μόνο οι πελάτες του συγκεκριμένου τύπου αποθήκης.
                .filter(c -> c.warehouseType.equalsIgnoreCase(warehouseType))
                .collect(Collectors.toList());

        SimulationEngine engine = new SimulationEngine(customers, routeDistances, selectedTruck, warehouseType);	// Σε αυτό το σημείο δημιουργείται ένα νέο αντικείμενο SimulationEngine, λαμβάνοντας ως παραμέτρους τη λίστα πελατών που φιλτράραμε, το routeDistances (Map με διαδρομές → αποστάσεις), το φορτηγό που επέλεξε ο χρήστης και τον τύπο αποθήκης, από την στιγμή που το SimulationEngine είναι υπεύθυνο για όλη τη λογική υπολογισμού κερδών.

        Map<String, Double> profits = engine.calculateProfitForAllRoutes();		// Εδώ καλείται η μέθοδος calculateProfitForAllRoutes() του SimulationEngine, που υπολογίζει το προβλεπόμενο κέρδος για κάθε route για 6 μήνες (26 εβδομάδες), όπου επιστρέφεται ένα Map με Key: κωδικός route (String, π.χ., "R1", "R2") και Value: συνολικό κέρδος για 6 μήνες (Double), ενώ το αποτέλεσμα αποθηκεύεται στη μεταβλητή profits.

        resultArea.setText("📊 PROFIT PER ROUTE (6 MONTHS)\n\n");	// Το resultArea είναι το text area του GUI, όπου μέσω του setText() καθαρίζεται το προηγούμενο περιεχόμενο και προστίθεται ο τίτλος του report, για εμφάνιση των αποτελεσμάτων.

        profits.forEach((route, profit) -> {	// Η μέθοδος forEach() διατρέχει όλα τα στοιχεία του Map<String, Double> profits, όπου σε κάθε επανάληψη για κάθε ζεύγος (route, profit), προστίθεται στο resultArea ένα γραμμικό string (πχ. "Route R1: 12345.68 €"), καθώς το String.format("%.2f", profit) διασφαλίζει ότι το κέρδος εμφανίζεται με 2 δεκαδικά ψηφία, ενώ μέσω της μεθόδου append() διατηρούνται τα προηγούμενα δεδομένα και προστίθεται η νέα γραμμή, από την στιγμή που αυτό επαναλαμβάνεται για όλα τα routes, δημιουργώντας λίστα με όλα τα κέρδη ανά route στο GUI και τέλος το GUI εμφανίζει όλα τα routes με τα αντίστοιχα κέρδη τους για 6 μήνες.
            resultArea.append(
                "Route " + route + ": " +
                String.format("%.2f", profit) + " €\n"
            );
        });		// εδώ κλείνει το forEach
    }

    
    // Πρακτικά παρακάτω δημιουργούμε το GUI αντικείμενο → Προγραμματίζουμε την εμφάνισή του στο Event Dispatch Thread και το GUI παράθυρο εμφανίζεται στον χρήστη. Στην ουσία ξεκινά το πρόγραμμα → Προγραμματίζει τη δημιουργία του GUI στο σωστό thread → Το GUI δημιουργείται και εμφανίζεται στην οθόνη και τέλος γίνεται σωστή και ασφαλής διαχείριση όλων των events.
    public static void main(String[] args) {	// Η μέθοδος main είναι η κύρια μέθοδος εκκίνησης σε μια Java εφαρμογή, όπου το πρόγραμμα ξεκινά πάντα από την main μέθοδο, η οποία είναι public (ορατή και προσβάσιμη από παντού), καθώς ό,τι γράψουμε μέσα στη main εκτελείται πρώτα.
        // Χρησιμοποιούμε invokeLater, διότι αν καλέσουμε απευθείας το new TruckRoutingSimulatorDB().setVisible(true) εκτός του EDT, μπορεί να έχουμε προβλήματα όπως ότι το GUI (το παράθυρο) δεν εμφανίζεται και δεν φορτώνεται σωστά ή σφάλματα concurrency, δηλαδή συγχρονισμού, αν κάποια άλλα events προσπαθήσουν να αλλάξουν τα components ταυτόχρονα, καθώς χρησιμοποιώντας SwingUtilities.invokeLater, η Java εγγυάται ότι όλα τα GUI operations τρέχουν με ασφάλεια στο σωστό thread.
    	SwingUtilities.invokeLater(() -> new TruckRoutingSimulatorDB().setVisible(true));		// Σε αυτό το σημείο το Swing (GUI framework της Java) απαιτεί ότι όλα τα GUI operations πρέπει να εκτελούνται στο Event Dispatch Thread (EDT), καθώς μέσω της μεθόδου invokeLater(Runnable) λαμβάνεται ένα Runnable. Στην ουσία καλείται η public static void μέθοδος invokeLater(Runnable runnable), η οποία προκαλεί το runnable να καλέσει τη μέθοδο εκτέλεσης στο νήμα αποστολής του συστήματος EventQueue, ενώ αυτό θα συμβεί μετά την επεξεργασία όλων των εκκρεμών συμβάντων με παράμετρο της μεθόδου ένα runnable(του οποίου η μέθοδος εκτέλεσης εκτελείται ασύγχρονα στο νήμα αποστολής συμβάντων του συστήματος EventQueue). Στην προκειμένη περίπτωση, δημιουργείται μία ανώνυμη εσωτερική κλάση, η οποία υλοποιεί την διεπαφή(interface-Runnable), όπου προγραμματίζεται αυτό το Runnable να εκτελεστεί αργότερα στο EDT, όταν η ουρά συμβάντων του GUI το επιτρέψει. Αυτό εξασφαλίζει ότι το GUI είναι thread-safe και δεν θα έχουμε προβλήματα με πολλαπλά threads που προσπαθούν να αλλάξουν τα components ταυτόχρονα. Μέσω χρήσης του Lambda Expression () -> new TruckRoutingSimulatorDB().setVisible(true) υλοποιείται το Runnable (με σύντομο τρόπο), δηλαδή δημιουργείται ένα νέο αντικείμενο GUI της κλάσης TruckRoutingSimulatorDB που είναι το GUI παράθυρο/πλαίσιο (JFrame), ενώ στην συνέχεια μέσω κλήσης της μεθόδου setVisible(true), εμφανίζεται το GUI παράθυρο στην οθόνη του χρήστη, όπου μέχρι να καλέσουμε το setVisible(true), το παράθυρο υπάρχει στη μνήμη αλλά δεν φαίνεται ακόμα στον χρήστη. Τέλος ο χρήστης βλέπει το παράθυρο της εφαρμογής, και από εκεί μπορούν να ξεκινήσουν όλα τα GUI events (κουμπιά, λίστες κ.λπ.).  
    }
    
}	// Εδώ κλείνει όλη η κλάση.	
