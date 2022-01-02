package org.tsir.common.services.communications.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * TaskDAO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-04-19T18:58:02.935Z[GMT]")
@JacksonXmlRootElement(localName = "TaskDAO")
@XmlRootElement(name = "TaskDAO")
@XmlAccessorType(XmlAccessType.FIELD)

public class TaskDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("name")
	@JacksonXmlProperty(localName = "name")
	private String name = null;

	@JsonProperty("registerPK")
	@JacksonXmlProperty(localName = "registerPK")
	private String registerPK = null;

	public TaskDTO name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Nombre de la tarea de comunicaciones a establecer.
	 * 
	 * @return name
	 **/
	@Schema(description = "Nombre de la tarea de comunicaciones a establecer. ")

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TaskDTO registerPK(String registerPK) {
		this.registerPK = registerPK;
		return this;
	}

	/**
	 * LLave primaria del registro para ubicación de la información a replicar.
	 * 
	 * @return registerPK
	 **/
	@Schema(description = "LLave primaria del registro para ubicación de la información a replicar. ")

	public String getRegisterPK() {
		return registerPK;
	}

	public void setRegisterPK(String registerPK) {
		this.registerPK = registerPK;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TaskDTO taskDAO = (TaskDTO) o;
		return Objects.equals(this.name, taskDAO.name) && Objects.equals(this.registerPK, taskDAO.registerPK);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, registerPK);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class TaskDAO {\n");

		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    registerPK: ").append(toIndentedString(registerPK)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
