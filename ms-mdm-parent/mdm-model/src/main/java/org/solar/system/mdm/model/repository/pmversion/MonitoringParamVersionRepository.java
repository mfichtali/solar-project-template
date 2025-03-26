package org.solar.system.mdm.model.repository.pmversion;

import org.solar.system.mdm.model.entities.pmversion.EndpointRef;
import org.solar.system.mdm.model.entities.pmversion.MonitoringParamVersion;
import org.solar.system.mdm.model.entities.pmversion.VersionRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MonitoringParamVersionRepository extends JpaRepository<MonitoringParamVersion, UUID> {

    List<MonitoringParamVersion> findByVersionRefAndActiveTrueAndLockFalse(VersionRef versionRef);
    
    @Query("select apv from MonitoringParamVersion apv " +
            " join fetch apv.versionRef v " +
            " join fetch apv.endpointRef e " +
            " where e.id = :edpId " +
            " and apv.deleted=false ")
    public List<MonitoringParamVersion> findByEndpoint(@Param("edpId") UUID edpId);
    
    @Query("select apv from MonitoringParamVersion apv " +
            " join fetch apv.versionRef v " +
            " where v.id = :versionId " +
            " and apv.deleted=false ")
    List<MonitoringParamVersion> findByVersion(@Param("versionId") UUID versionId);

    @Query("select apv from MonitoringParamVersion apv " +
            " join fetch apv.versionRef v " +
            " join fetch apv.endpointRef e " +
            " where e.id = :edpId and v.id = :versionId "+
            " and apv.deleted=false ")
    MonitoringParamVersion getOneByEndpointAndVersion(@Param("edpId") UUID edpId, @Param("versionId") UUID versionId);

    MonitoringParamVersion findByVersionRefAndEndpointRef(VersionRef version, EndpointRef endpoint);
    
    @Query("select apv from MonitoringParamVersion apv " +
            " join fetch apv.versionRef v " +
            " join fetch apv.endpointRef e " +
            " where v.id = :versionId " +
            " and apv.deleted=false ")
    List<MonitoringParamVersion> findByVersionToActivate(@Param("versionId") UUID versionId);
    
	@Modifying
	@Query("delete from MonitoringParamVersion m where m.deleted=true")
	void purgeApiParam();
}
