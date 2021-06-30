package mirai.noerla.plugin.timer;

import mirai.noerla.plugin.JavaPluginMain;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class ExchangeScheduler {

    public JobDetail getJobDetail(){
        return JobBuilder.newJob(ExchangerJob.class)
                .withDescription("调用ExchangerJob更新汇率")
                .build();
    }

    public Trigger getTrigger(){
        return TriggerBuilder.newTrigger()
                .withDescription("24小时执行一次")
                .startAt(new Date())
                .withSchedule(SimpleScheduleBuilder.repeatHourlyForever(24))
                .build();
    }

    public void startScheduler(){
        try{
            JavaPluginMain.INSTANCE.getLogger().info("开始更新汇率");
            //声明一个scheduler的工厂schedulerFactory
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            //通过schedulerFactory来实例化一个Scheduler
            Scheduler scheduler = schedulerFactory.getScheduler();
            //将Job和Trigger注册到scheduler容器中
            scheduler.scheduleJob(getJobDetail(),getTrigger());
            scheduler.start();
        } catch (Exception e){
            JavaPluginMain.INSTANCE.getLogger().error("汇率更新失败");
        }
    }
}
