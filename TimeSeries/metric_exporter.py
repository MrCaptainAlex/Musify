from prometheus_api_client import PrometheusConnect
from prometheus_api_client.utils import parse_datetime
from prometheus_api_client.metric_range_df import MetricRangeDataFrame
from datetime import *
import pandas as pd
import csv


def get_metric_value_in_time_range(metric_name, start_time, end_time, chunk_size):
    prom = PrometheusConnect(url="http://localhost:9090", disable_ssl=True)

    metric_data = prom.get_metric_range_data(
        metric_name=metric_name,
        start_time=start_time,
        end_time=end_time,
        chunk_size=chunk_size,
    )

    df = MetricRangeDataFrame(metric_data)
    return df

def fix_ts(ts):
    ts.index = pd.to_datetime(ts.index, unit="s", utc=True)
    ts = ts.drop(columns = '__name__')
    ts = ts.drop(columns = 'instance')
    ts = ts.drop(columns = 'job')
    ts = ts.astype(int)
    return ts

if __name__ == '__main__':
    
    start_time = datetime.now() - timedelta(hours=2)
    end_time = parse_datetime("now")
    chunk_size = timedelta(hours=1)
    
    # user
    u_ts = get_metric_value_in_time_range("user_counter_total", start_time, end_time, chunk_size)
    u_ts = fix_ts(u_ts)
    f = open('user_metrics.csv','w+')
    writer = csv.writer(f)
    u_ts.to_csv('user_metrics.csv')
    
    # catalog
    ca_ts = get_metric_value_in_time_range("catalog_counter_total", start_time, end_time, chunk_size)
    ca_ts = fix_ts(ca_ts)
    f = open('catalog_metrics.csv','w+')
    writer = csv.writer(f)
    ca_ts.to_csv('catalog_metrics.csv')
    
    # payment
    pa_ts = get_metric_value_in_time_range("pay_counter_total", start_time, end_time, chunk_size)
    pa_ts = fix_ts(pa_ts)
    f = open('pay_metrics.csv','w+')
    writer = csv.writer(f)
    pa_ts.to_csv('pay_metrics.csv')
