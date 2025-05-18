package com.synchrotech.commandcenter.service.module;

import com.synchrotech.commandcenter.model.module.Calendar;
import com.synchrotech.commandcenter.model.module.Appointment;
import com.synchrotech.commandcenter.model.module.Resource;
import com.synchrotech.commandcenter.model.module.Availability;
import java.util.List;

/**
 * Service interface for calendar module operations.
 */
public interface CalendarService {
    Calendar getCalendar(String tenantId);
    List<Appointment> getAppointments(String tenantId);
    Appointment getAppointmentById(String tenantId, String appointmentId);
    Appointment createAppointment(String tenantId, Appointment appointment);
    Appointment updateAppointment(String tenantId, String appointmentId, Appointment appointment);
    void deleteAppointment(String tenantId, String appointmentId);
    List<Resource> getResources(String tenantId);
    Resource getResourceById(String tenantId, String resourceId);
    Resource createResource(String tenantId, Resource resource);
    Resource updateResource(String tenantId, String resourceId, Resource resource);
    void deleteResource(String tenantId, String resourceId);
    List<Availability> getAvailability(String tenantId, String resourceId);
    Availability setAvailability(String tenantId, String resourceId, Availability availability);
} 