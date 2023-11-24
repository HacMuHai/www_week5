package vn.edu.iuh.fit.backend.repositories;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.iuh.fit.backend.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Transactional
    @Modifying
    @Query("""
            update Address a set a.number = ?1, a.street = ?2, a.city = ?3, a.zipcode = ?4, a.country = ?5
            where a.id = ?6""")
    int updateNumberAndStreetAndCityAndZipcodeAndCountryById(String number, String street, String city, String zipcode, CountryCode country, long id);

}