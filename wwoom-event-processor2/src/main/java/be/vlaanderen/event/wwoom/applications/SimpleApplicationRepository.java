package be.vlaanderen.event.wwoom.applications;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleApplicationRepository implements ApplicationRepository {

    private final String key;

    @Override
    public Application getApplication(String applicationId) {
        return new Application(applicationId, key);
    }
}
