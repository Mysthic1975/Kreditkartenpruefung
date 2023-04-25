import javax.swing.*;

class Main {
    public static void main(String[] args) {
        // Erstellt das Hauptfenster
        JFrame frame = new JFrame("Kreditkartenprüfung");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Erstellt die Schaltflächen und das Eingabefeld
        JButton checkButton = new JButton("Überprüfen");
        JButton calculateButton = new JButton("Prüfziffer berechnen");
        JTextField inputField = new JTextField(16);
        JLabel resultLabel = new JLabel();

        // Fügt die Schaltflächen und das Eingabefeld zum Hauptfenster hinzu
        JPanel panel = new JPanel();
        panel.add(checkButton);
        panel.add(calculateButton);
        panel.add(inputField);
        panel.add(resultLabel);
        frame.add(panel);

        // Fügt einen ActionListener zur Überprüfungsschaltfläche hinzu
        checkButton.addActionListener(e -> {
            String kkNummer = inputField.getText().replaceAll("\\s+", "");
            if (isInvalidInput(kkNummer)) {
                resultLabel.setText("Ungültige Eingabe. Bitte geben Sie eine gültige Kreditkartennummer ein.");
            } else {
                int checkDigit = getCheckDigit(kkNummer);
                boolean isValid = Check(kkNummer);
                if (isValid) {
                    resultLabel.setText("Die Kreditkartennummer ist gültig. Prüfziffer: " + checkDigit);
                } else {
                    resultLabel.setText("Die Kreditkartennummer ist ungültig. Prüfziffer: " + checkDigit);
                }
            }
        });

        // Fügt einen ActionListener zur Berechnungsschaltfläche hinzu
        calculateButton.addActionListener(e -> {
            String kkNummer = inputField.getText().replaceAll("\\s+", "");
            if (isInvalidInput(kkNummer) || kkNummer.length() != 15) {
                resultLabel.setText("Ungültige Eingabe. Bitte geben Sie die ersten 15 Ziffern einer gültigen Kreditkartennummer ein.");
            } else {
                int checkDigit = calculateCheckDigit(kkNummer);
                resultLabel.setText("Die berechnete Prüfziffer ist: " + checkDigit);
            }
        });

        // Zeigt das Hauptfenster an
        frame.setVisible(true);
    }

    // Überprüft, ob die Eingabe ungültig ist
    public static boolean isInvalidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isDigit(c)) {
                return true;
            }        }
        return false;
    }

    // Gibt die Prüfziffer einer Kreditkartennummer zurück
    public static int getCheckDigit(String kkNummer) {
        return Integer.parseInt(kkNummer.substring(kkNummer.length() - 1));
    }

    // Überprüft die Gültigkeit einer Kreditkartennummer
    public static boolean Check(String kkNummer) {
        int sum = calculateSum(kkNummer, false);
        return (sum % 10 == 0);
    }

    // Berechnet die Prüfziffer für eine Kreditkartennummer
    public static int calculateCheckDigit(String kkNummer) {
        int sum = calculateSum(kkNummer, true);
        return (10 - (sum % 10)) % 10;
    }

    // Berechnet die Summe der Zahlen in einer Kreditkartennummer unter Verwendung der Luhn-Formel
    public static int calculateSum(String kkNummer, boolean alternate) {
        int sum = 0;
        for (int i = kkNummer.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(kkNummer.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return sum;
    }
}