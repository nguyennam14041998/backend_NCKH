package com.api.backend.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.api.backend.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.api.backend.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.api.backend.domain.User.class.getName());
            createCache(cm, com.api.backend.domain.Authority.class.getName());
            createCache(cm, com.api.backend.domain.User.class.getName() + ".authorities");
            createCache(cm, com.api.backend.domain.Donvi.class.getName());
            createCache(cm, com.api.backend.domain.Donvi.class.getName() + ".nhansus");
            createCache(cm, com.api.backend.domain.Linhvuc.class.getName());
            createCache(cm, com.api.backend.domain.Linhvuc.class.getName() + ".detais");
            createCache(cm, com.api.backend.domain.Capdetai.class.getName());
            createCache(cm, com.api.backend.domain.Capdetai.class.getName() + ".detais");
            createCache(cm, com.api.backend.domain.Chucdanh.class.getName());
            createCache(cm, com.api.backend.domain.Chucdanh.class.getName() + ".nhansus");
            createCache(cm, com.api.backend.domain.Hocham.class.getName());
            createCache(cm, com.api.backend.domain.Hocham.class.getName() + ".nhansus");
            createCache(cm, com.api.backend.domain.Nhansu.class.getName());
            createCache(cm, com.api.backend.domain.Nhansu.class.getName() + ".chunhiems");
            createCache(cm, com.api.backend.domain.Nhansu.class.getName() + ".nhansuthamgias");
            createCache(cm, com.api.backend.domain.Nhansuthamgia.class.getName());
            createCache(cm, com.api.backend.domain.Chunhiem.class.getName());
            createCache(cm, com.api.backend.domain.Chunhiem.class.getName() + ".detais");
            createCache(cm, com.api.backend.domain.Coquanphoihop.class.getName());
            createCache(cm, com.api.backend.domain.Coquanphoihop.class.getName() + ".coquanphoihopthamgias");
            createCache(cm, com.api.backend.domain.Coquanphoihopthamgia.class.getName());
            createCache(cm, com.api.backend.domain.Nguonkinhphi.class.getName());
            createCache(cm, com.api.backend.domain.Detai.class.getName());
            createCache(cm, com.api.backend.domain.Detai.class.getName() + ".tiendos");
            createCache(cm, com.api.backend.domain.Detai.class.getName() + ".upfiles");
            createCache(cm, com.api.backend.domain.Detai.class.getName() + ".nhansuthamgias");
            createCache(cm, com.api.backend.domain.Detai.class.getName() + ".nguonkinhphis");
            createCache(cm, com.api.backend.domain.Detai.class.getName() + ".coquanphoihopthamgias");
            createCache(cm, com.api.backend.domain.Detai.class.getName() + ".danhgias");
            createCache(cm, com.api.backend.domain.Detai.class.getName() + ".dutoanKPS");
            createCache(cm, com.api.backend.domain.Detai.class.getName() + ".danhsachbaibaos");
            createCache(cm, com.api.backend.domain.DutoanKP.class.getName());
            createCache(cm, com.api.backend.domain.DutoanKP.class.getName() + ".dutoanKPCTS");
            createCache(cm, com.api.backend.domain.DutoanKPCT.class.getName());
            createCache(cm, com.api.backend.domain.NoidungDT.class.getName());
            createCache(cm, com.api.backend.domain.NoidungDT.class.getName() + ".dutoanKPCTS");
            createCache(cm, com.api.backend.domain.Tiendo.class.getName());
            createCache(cm, com.api.backend.domain.Tiendo.class.getName() + ".upfiles");
            createCache(cm, com.api.backend.domain.Danhgia.class.getName());
            createCache(cm, com.api.backend.domain.Danhgia.class.getName() + ".danhgiaCTS");
            createCache(cm, com.api.backend.domain.DanhgiaCT.class.getName());
            createCache(cm, com.api.backend.domain.Noidungdanhgia.class.getName());
            createCache(cm, com.api.backend.domain.Noidungdanhgia.class.getName() + ".danhgiaCTS");
            createCache(cm, com.api.backend.domain.Hoidongdanhgia.class.getName());
            createCache(cm, com.api.backend.domain.Hoidongdanhgia.class.getName() + ".detais");
            createCache(cm, com.api.backend.domain.Hoidongdanhgia.class.getName() + ".thanhvienHDS");
            createCache(cm, com.api.backend.domain.ThanhvienHD.class.getName());
            createCache(cm, com.api.backend.domain.Upfile.class.getName());
            createCache(cm, com.api.backend.domain.Danhsachbaibao.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
