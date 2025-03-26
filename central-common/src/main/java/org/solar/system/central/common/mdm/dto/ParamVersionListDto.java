package org.solar.system.central.common.mdm.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.solar.system.central.common.mdm.Views;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
@Builder
@JsonInclude(value = Include.NON_NULL)
public class ParamVersionListDto implements Serializable{
    
    @JsonView(Views.NoIdentifierView.class)
    private ModuleInfoDto module;
    
    @JsonView(Views.NoIdentifierView.class)
    private List<VersionListDto> versions;
    
    public static ParamVersionListDto defaultOne() {
        return ParamVersionListDto.builder().versions(Collections.emptyList()).build();
    }

}
