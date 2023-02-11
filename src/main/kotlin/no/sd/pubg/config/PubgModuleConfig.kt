package no.sd.pubg.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EntityScan(basePackages = ["no.sd.pubg"])
@ComponentScan(basePackages = ["no.sd.pubg"])
class PubgModuleConfig {

}