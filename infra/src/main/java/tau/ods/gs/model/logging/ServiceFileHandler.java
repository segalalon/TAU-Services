package tau.ods.gs.model.logging;

import java.util.logging.*;
import java.io.IOException;
public class ServiceFileHandler extends FileHandler {
    public ServiceFileHandler() throws IOException, SecurityException {
        init();
    }
    public ServiceFileHandler(String pattern, long limit, int count, boolean append) throws IOException {
        super(pattern, limit, count, append);
        init();
    }
    private void init() {
        this.setFilter(new Filter() {
            @Override
            public boolean isLoggable(LogRecord record) {
                return record.getSourceClassName().startsWith("com.gs.usecase");
            }
        });
    }
}
