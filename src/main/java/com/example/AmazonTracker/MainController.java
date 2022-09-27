package com.example.AmazonTracker;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import org.springframework.ui.Model;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public String addNewUser (@RequestParam String url, RedirectAttributes redirAttrs) {
        // @RequestParam means it is a parameter from the GET or POST request
        if(validateURL(url)) {
            //add alert here saying url is invalid
            redirAttrs.addFlashAttribute("message", "Website is invalid");
            return "redirect:/demo/greet";
        }
        //validate this is from amazon

        String title = null;
        double price = -1;
        String imgURL = null;
        try {
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36";
            Document doc = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .header("Accept-Language", "gzip, deflate, br")
                    .header("Accept-Encoding", "en-US,en;q=0.9")
                    .get();
            if(!soldByAmazon(doc)) {
                redirAttrs.addFlashAttribute("message", "Product MUST be sold by Amazon");//error message
                return "redirect:/demo/greet";
            }
            title = findTitle(doc);
            price = findPrice(doc);
            imgURL = findImageURL(doc);
            if(title == null || price == -1 || imgURL == null) {
                File logs = new File("logs.txt");
                FileWriter output = new FileWriter(logs,true);
                output.write(url);
                output.write(": title,price, or image cannot be found\n");
                output.close();
                redirAttrs.addFlashAttribute("message", "Image/price/title cannot be found");
                return "redirect:/demo/greet";
            }
        }
        catch(FileNotFoundException e) { //doc
            e.printStackTrace();
            redirAttrs.addFlashAttribute("message", "File Not found Exception");
            return "redirect:/demo/greet";
        }
        catch(IOException e) {
            e.printStackTrace();
            redirAttrs.addFlashAttribute("message", "IO Exception");
            return "redirect:/demo/greet";
        }
        User n = new User();
        n.setTitle(title);
        n.setPrice(price);
        n.setUrl(url);
        n.setCreated_at(new Date());
        n.setUpdated_at(new Date());
        userRepository.save(n);
        return "redirect:/demo/greet";
    }
    @GetMapping(path="/greet")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name); //add this to templates
        //add all database stuff to add Attribute
        return "demo"; //go to template name
    }
    //@Scheduled(fixedRate = 5000)
    public void scheduledWebCall() {
        System.out.println("hello world");
        //make the webscape call here after a fixed amount of time
    }
    public boolean soldByAmazon(Document doc) {
        Element isAmazon = doc.select("#tabular-buybox > div.tabular-buybox-container > div:nth-child(4) > div > span").first();
        if(isAmazon == null) {
            //add more methods if sold by amazon but not recognized
            return false;//notsoldbyAmazon
        }
        else if (isAmazon.text().toLowerCase().contains("amazon.com")) {
            return true; //good
        }
        return false; // found but not sold by amazon

    }
    public String findImageURL(Document doc) {
        Element imgURL = doc.select("#landingImage").first();
        if(imgURL == null) {
            imgURL = doc.select("#imgBlkFront").first();
            if(imgURL == null) {
                return null;
            }
            return imgURL.absUrl("src");
        }
        return imgURL.absUrl("src");
    }
    public String findTitle(Document doc){
        Element productTitle = doc.getElementById("productTitle");
        if(productTitle == null) {
            //try new method of finding product title, also if problem occurs then keep a log of it
            return null;
        }
        return productTitle.text();
    }
    public double findPrice(Document doc) {
        Element price = doc.getElementById("price");//jujutsu kaisen manga
        if(price == null) {
            price = doc.select("#corePrice_feature_div > div > span > span:nth-child(2)").first(); // you have to the right to remain innocent
            if(price == null) {
                price = doc.select("#corePrice_feature_div > div > span.a-price.a-text-price.header-price.a-size-base.a-text-normal.a-color-price > span:nth-child(2)").first(); // one-time purchase
                if(price == null) {
                    return -1; //if nothing else works
                }
                double sPrice = Double.parseDouble(price.text().substring(1));
                return sPrice;
            }
            double sPrice = Double.parseDouble(price.text().substring(1));
            return sPrice;
            //try more methods
        }
        double sPrice = Double.parseDouble(price.text().substring(1));
        return sPrice;
    }
    public boolean validateURL(String url) {
        if(!url.contains("amazon.com") || !url.contains("Amazon.com")) {//validate website, must be Amazon
            try {
                File logs = new File("logs.txt");
                FileWriter output = new FileWriter(logs,true);
                output.write(url);
                output.write(": Url is invalid\n");
                output.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
