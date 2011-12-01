package com.google.eclipse.mechanic.internal;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.eclipse.core.variables.IValueVariable;
import org.eclipse.core.variables.IValueVariableInitializer;

import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * Variable initializer that sets the initial value to the configuration directory for the
 * Workspace Mechanic.
 */
public class MechanicConfigurationVariableInitializer implements IValueVariableInitializer {
  private final MechanicLog mechanicLog = MechanicLog.getDefault();

  public void initialize(IValueVariable variable) {
    String separator = getProperties().getProperty("file.separator");
    String eclipseInstallationLocation = getProperties().getProperty("osgi.install.area");
    try {
      if (eclipseInstallationLocation != null){
        URI uri = new URI(eclipseInstallationLocation);
        String fullLocation = new File(uri).getPath() + separator +
            "configuration" + separator + "com.google.eclipse.mechanic";
        variable.setValue(fullLocation);
      } else {
        this.mechanicLog.logWarning("Eclipse installation location not found");
      }
    } catch (URISyntaxException e) {
      this.mechanicLog.logError(e, "Can't compute Mechanic Configuration directory: %s",
          eclipseInstallationLocation);
    }
  }

  protected Properties getProperties() {
    return System.getProperties();
  }
}