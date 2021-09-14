package com.galvanize.requestbody;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AccessingVariables {
    //Request Parameters with Car

    //@RequestParam to access each parameter individually
//    @GetMapping("/car")
//    public String formatCarString(@RequestParam String make, @RequestParam String model, @RequestParam(required = false, defaultValue = "2010") String year) {
//        return String.format("The %s %s was made in %s", make, model, year);
//    }

//    @RequestParam to access all of the parameters as a Map
//    @GetMapping("/car")
//    public String formatCarString(@RequestParam Map<String, String> parameterMap) {
//        return String.format("The %s %s was made in %s", parameterMap.get("make"), parameterMap.get("model"), parameterMap.get("year"));
//
//    }

//    Access our parameters through a Java Object
    @GetMapping("/car")
    public String formatCarString(Car car) {
        return String.format("The %s %s was made in %s", car.getMake(), car.getModel(), car.getYear());

    }


    //Path Variables with Area of a Rectangle
    @GetMapping("/rectangle/{length}/{width}")
    public String calculateAreaOfRectangle(@PathVariable int length, @PathVariable int width) {
        return String.format("The area of the rectangle is %d", length * width);
    }


    //Request Body with Social Media Comment
    //Access body as String
//    @PostMapping("/comments")
//    public String formatTheComment(@RequestBody String body) {
//        //spllit string, format string, return
//        return body;
//    }

    //Access body as Map
//    @PostMapping("/comments")
//    public String formatTheComment(@RequestBody Map<String, String> body) {
//        //spllit string, format string, return
//        return String.format("%s says %s", body.get("author"), body.get("content"));
//    }

    @PostMapping("/comments")
    public String formatTheComment( Comment comment) {
        //spllit string, format string, return
        return String.format("%s says %s", comment.getAuthor(), comment.getContent());
    }


}
