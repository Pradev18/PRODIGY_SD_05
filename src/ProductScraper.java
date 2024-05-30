package scraper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProductScraper extends JFrame {

    private JButton scrapeButton;
    private JTextField fileNameField;

    public ProductScraper() {
        // Set up the frame
        setTitle("Product Scraper");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the text field for file name
        fileNameField = new JTextField("products.csv", 20);

        // Set up the button
        scrapeButton = new JButton("Scrape Products");
        scrapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrapeProducts();
            }
        });

        // Set up the layout
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter file name:"));
        panel.add(fileNameField);
        panel.add(scrapeButton);

        // Add panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }

    private void scrapeProducts() {
        try {
            // Specify the path to your local HTML file
            String filePath = "C:\\Users\\prade\\Desktop\\Prodigy Infotech\\ProductScraper\\src\\scraper\\products.html";
            File input = new File(filePath);

            // Parse the local HTML file
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements products = doc.select(".product");

            String fileName = fileNameField.getText();
            if (!fileName.endsWith(".csv")) {
                fileName += ".csv";
            }

            FileWriter csvWriter = new FileWriter(fileName);
            csvWriter.append("Name,Price,Rating\n");

            for (Element product : products) {
                String name = product.select(".product-name").text();
                String price = product.select(".product-price").text();
                String rating = product.select(".product-rating").text();

                csvWriter.append(name);
                csvWriter.append(",");
                csvWriter.append(price);
                csvWriter.append(",");
                csvWriter.append(rating);
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
            JOptionPane.showMessageDialog(this, "Products scraped successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while scraping products.");
        }
    }

    public static void main(String[] args) {
        new ProductScraper();
    }
}


