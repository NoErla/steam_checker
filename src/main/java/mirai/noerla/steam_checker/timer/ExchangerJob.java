package mirai.noerla.steam_checker.timer;

import mirai.noerla.steam_checker.crawler.ExchangeCrawler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ExchangerJob implements Job {

    ExchangeCrawler exchangeCrawler = ExchangeCrawler.INSTANCE;

    @Override
    public void execute(JobExecutionContext context) {
        exchangeCrawler.updateExchange();
    }
}
