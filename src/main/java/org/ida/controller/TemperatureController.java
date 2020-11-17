package org.ida.controller;


import java.util.Date;
import java.util.List;

import org.ida.ResouceNotFoundException;
import org.ida.entities.LevelTemp;
import org.ida.entities.Temperature;

import org.ida.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;






@RestController
@RequestMapping("/api")
public class TemperatureController {
	
	

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	TemperatureRepository temperatureRepository;

	@PostMapping("/temperatures")
	public Temperature addTemperature(@RequestBody Temperature temperature) {
		temperatureRepository.save(temperature);
		return temperature;
	}
	
	
	@RequestMapping("/temp")
	public Temperature addTemperatur() {
		Date date = new Date();
		Temperature temp =new Temperature("0","nameSensor",10.4,date.toInstant(),1); 
		
		temperatureRepository.save(temp);
		return temp;
	}
	
	@GetMapping("/temperature/{toke}")
    public ResponseEntity<Temperature> findById(@PathVariable("toke") String token){
       Temperature product = temperatureRepository.findById(token).orElseThrow(
                () -> new ResouceNotFoundException("this token not found" + token));
        return ResponseEntity.ok().body(product);
    }
	
	

    @GetMapping("/temperatures")
    public List<Temperature> getTemperature(){

        return temperatureRepository.findAll();
    }
    
    @DeleteMapping("delete/{toke}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "toke") String token) {
        Temperature temperature = temperatureRepository.findById(token).orElseThrow(
                () -> new ResouceNotFoundException("this token not found::: " + token));
        temperatureRepository.delete(temperature);
        return ResponseEntity.ok().build();
    }
    
    @RequestMapping("/level")
    public Temperature getServiceTemperqture(){
    	
    	Temperature temperature = restTemplate.getForObject("http://localhost:9999/api/aaa", Temperature.class );
    	return temperature;
    }
    
    
    @RequestMapping("/tem/level")
    public Temperature levelTemp(){
    	Temperature temperature = getServiceTemperqture();
    	System.out.println(temperature.toString());
    	LevelTemp level =new LevelTemp();
    	
    	
    		if(temperature.getValueTemperature()> level.getMeasure1() && temperature.getValueTemperature()<level.getMeasure0()){
    			temperature.setLevel(level.getLevel1());
    			addTemperature(temperature);
    		}
    		else
    			if(temperature.getValueTemperature()> level.getMeasure0()) {
    				temperature.setLevel(level.getLevel0());
    				addTemperature(temperature);
    			}
    			else {
    				temperature.setLevel(level.getLevel2());
    				addTemperature(temperature);
    			}
    	
    	return temperature;
    }


}
