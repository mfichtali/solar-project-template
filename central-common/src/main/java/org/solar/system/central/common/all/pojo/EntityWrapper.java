package org.solar.system.central.common.all.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class EntityWrapper<T> extends BaseWrapper {

	private static final long serialVersionUID = 1L;
	private transient T result;

}
