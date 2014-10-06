package com.jamasoftware.maven.enforcer.releasedev;

import static java.lang.String.format;

import java.util.List;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.model.Profile;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

public class ReleaseDevEnforcerRule implements EnforcerRule {

    @Override
    public void execute(EnforcerRuleHelper helper) throws EnforcerRuleException {
        try {
            boolean releaseProfile = false;
            boolean devProfile = false;
            MavenProject project = (MavenProject) helper.evaluate("${project}");
            List<Profile> activeProfiles = project.getActiveProfiles();
            for (Profile profile : activeProfiles) {
                if ("release".equals(profile.getId())) {
                    releaseProfile = true;
                }
                if ("dev".equals(profile.getId())) {
                    devProfile = true;
                }
            }
            evaluateResults(helper, releaseProfile, devProfile);
        }
        catch (ExpressionEvaluationException exception) {
            throw new EnforcerRuleException("Unable to lookup an expression", exception);
        }
    }

    private void evaluateResults(EnforcerRuleHelper helper, boolean releaseProfile,
            boolean devProfile) throws EnforcerRuleException {
        if (releaseProfile ^ devProfile) {
            Log log = helper.getLog();
            log.info("good to go: " + status(releaseProfile, devProfile));
        }
        else {
            String message = "Module requires profile \"release\" XOR \"dev\"; %s";
            throw new EnforcerRuleException(format(message, status(releaseProfile, devProfile)));
        }
    }

    private String status(boolean releaseProfile, boolean devProfile) {
        return "\"release\" profile " + status(releaseProfile) + "; \"dev\" profile "
                + status(devProfile);
    }

    private String status(boolean releaseProfile) {
        return releaseProfile ? "activated" : "not activated";
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public boolean isResultValid(EnforcerRule cachedRule) {
        return false;
    }

    @Override
    public String getCacheId() {
        return null;
    }

}
