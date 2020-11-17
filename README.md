# Cassandra and spring boot

get a Json (field of an object of type Temperature) and depending on the value of the temperature we will add the level of the temprerature in the object temperature(entitie)

# In application.yml

    spring:
      data:
        cassandra:
          port: 9042
          contact-points: 127.0.0.1
          keyspace-name: mydb
          schemaAction: CREATE_IF_NOT_EXISTS
 
# In Controller
    
   
   
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
    } }
# In Repository 
    @Repository
      public interface TemperatureRepository extends CassandraRepository<Temperature,String>{

      }
# in entity
    @Table
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Temperature {
	
	@PrimaryKey
	private String toke;
	private String nameSensor;
	private double valueTemperature;
	private Instant date; 
	private int level;
	}
# In docker-Compose
you need a connection if you use a docker container for cassandra database

   version: '3.1'
   services:
      mycassandra:
      image: cassandra
      container_name: cassandra_analuse_service
      ports:
         - "9042:9042"
