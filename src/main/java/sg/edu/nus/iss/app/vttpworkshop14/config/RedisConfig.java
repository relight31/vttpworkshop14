package sg.edu.nus.iss.app.vttpworkshop14.config;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    private Logger logger = Logger.getLogger(RedisConfig.class.getName());
    @Value("${spring.redis.host}") //defines redis host name
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Bean //standalone configuration
    @Scope("singleton")
    public RedisTemplate <String, Object> RedisTemplate(){
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(); //instantiate

        logger.log(Level.INFO, "redisHost > " + redisHost + " redisPort "+ redisPort);

        config.setHostName(redisHost); //to know what redis db to connect to
        config.setPort(redisPort.get());

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config,jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String,Object> template = new RedisTemplate<String, Object>();

        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer()); //setting up what type of values can be stored
        template.setHashKeySerializer(new StringRedisSerializer()); //setting up what type of values can be stored in the map

        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());

        template.setValueSerializer(serializer);

        return template;
    }
}
