package mirai.noerla.plugin.timer;

import mirai.noerla.plugin.network.ExchangeCrawler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ExchangerJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ExchangeCrawler.getExchange();
    }
}
