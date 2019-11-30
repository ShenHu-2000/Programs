import numpy as np
import pandas as pd
import os
import matplotlib.pyplot as plt
import seaborn as sns


def wind_direction_conversion(degree):
    """
    Convert wind direction in degree to categorical direction.
    @param degree: wind direction in degree
    @return numeric category:
                0 -> N  (degree < 22.5 && degree >= 337.5)
                1 -> NE ( 22.5 <= degree <  67.5)
                2 -> E  ( 67.5 <= degree < 112.5)
                3 -> SE (112.5 <= degree < 157.5)
                4 -> S  (157.5 <= degree < 202.5)
                5 -> SW (202.5 <= degree < 247.5)
                6 -> W  (247.5 <= degree < 292.5)
                7 -> NW (292.5 <= degree < 337.5)
    """
    if np.isnan(degree):
        return
    return ((degree + 22.5) // 45) % 8


def plot_wtrain(wtrain, site_id, start_month=1, start_day=1, end_month=12, end_day=None):
    """
    Plot line charts of features in weather training set with given site_id and time range.
    @return None (will print a 4x2 graph as side-effect)
    """
    # fill end day and check dates valid
    end_days = [-1, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if end_day is None:
        end_day = end_days[end_month]
    assert all((m >= 1) & (m <= 12) for m in [start_month, end_month]), "Invalid month"
    assert all((d >= 1) & (d <= end_days[end_month]) for d in [start_day, end_day]), "Invalid day"
    
    # filter dataframe
    wtrain['timestamp'] = pd.to_datetime(wtrain['timestamp'])
    start_time = pd.to_datetime("2016-{0}-{1} 00:00:00"\
                                .format(str(start_month).zfill(2), str(start_day).zfill(2)))
    end_time = pd.to_datetime("2016-{0}-{1} 23:00:00"\
                              .format(str(end_month).zfill(2), str(end_day).zfill(2)))
    df = wtrain[(wtrain.site_id == site_id) &
                (wtrain.timestamp >= start_time) &
                (wtrain.timestamp <= end_time)].reset_index(drop=True)
    nrows = df.shape[0]
    assert nrows > 0, "Invalid filter parameters"
    
    # plot
    fig, axes = plt.subplots(4, 2, figsize=(15, 20))
    
    # construct title and word description
    title_strs = ['Features Plots of Weather Training Set',
                  'site_id = {0}'.format(site_id),
                  'time_range = {0} ~ {1}\n'.format(start_time, end_time),
                  'x-axis represents timestamp:']
    for p in range(20, 100, 20):
        cur_idx = int(np.percentile(df.index, p))
        cur_idx_str = str(cur_idx).zfill(len(str(nrows)))
        cur_time = df.timestamp.iloc[cur_idx]
        title_strs.append("P{0}: x = {1} -> {2}".format(p, cur_idx_str, cur_time))                       
    title = '\n'.join(title_strs)
    fig.suptitle(title, size='x-large')

    # fill subplots                     
    cols_to_plot = df.columns[2:]
    for i, col in enumerate(cols_to_plot):
        cur_ax = axes[i//2][i%2]
        df[col].plot(ax=cur_ax)
        cur_ax.set_title(col)
    
    
def plot_wtrain_bar_diagram(wtrain, site_id, start_month=1, start_day=1, end_month=12, end_day=None):
    """
    Plot bar charts of features in weather training set with given site_id and time range.
    @return None (will print a 4x2 graph as side-effect)
    """
    # fill end day and check dates valid
    end_days = [-1, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if end_day is None:
        end_day = end_days[end_month]
    assert all((m >= 1) & (m <= 12) for m in [start_month, end_month]), "Invalid month"
    assert all((d >= 1) & (d <= end_days[end_month]) for d in [start_day, end_day]), "Invalid day"
    
    # filter dataframe
    wtrain['timestamp'] = pd.to_datetime(wtrain['timestamp'])
    start_time = pd.to_datetime("2016-{0}-{1} 00:00:00"\
                                .format(str(start_month).zfill(2), str(start_day).zfill(2)))
    end_time = pd.to_datetime("2016-{0}-{1} 23:00:00"\
                              .format(str(end_month).zfill(2), str(end_day).zfill(2)))
    df = wtrain[(wtrain.site_id == site_id) &
                (wtrain.timestamp >= start_time) &
                (wtrain.timestamp <= end_time)].reset_index(drop=True)
    nrows = df.shape[0]
    assert nrows > 0, "Invalid filter parameters"
    # plot
    fig, axes = plt.subplots(4, 2, figsize=(15, 20))
    
    # construct title and word description
    title_strs = ['Features Plots of Weather Training Set',
                  'site_id = {0}'.format(site_id),
                  'time_range = {0} ~ {1}\n'.format(start_time, end_time),
                  'x-axis represents timestamp:']
    for p in range(20, 100, 20):
        cur_idx = int(np.percentile(df.index, p))
        cur_idx_str = str(cur_idx).zfill(len(str(nrows)))
        cur_time = df.timestamp.iloc[cur_idx]
        title_strs.append("P{0}: x = {1} -> {2}".format(p, cur_idx_str, cur_time))                       
    title = '\n'.join(title_strs)
    fig.suptitle(title, size='x-large')
    # fill subplots                     
    cols_to_plot = df.columns[2:]
    for i, col in enumerate(cols_to_plot):
        cur_ax = axes[i//2][i%2]
        df[col].plot(kind='hist',ax=cur_ax,bins=20)
        cur_ax.set_title(col)
        
        
def plot_wtrain_histogram(wtrain, site_id, start_month=1, start_day=1, end_month=12, end_day=None):
    """
    Plot histograms of features in weather training set with given site_id and time range.
    @return None (will print a 4x2 graph as side-effect)
    """
    # fill end day and check dates valid
    end_days = [-1, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if end_day is None:
        end_day = end_days[end_month]
    assert all((m >= 1) & (m <= 12) for m in [start_month, end_month]), "Invalid month"
    assert all((d >= 1) & (d <= end_days[end_month]) for d in [start_day, end_day]), "Invalid day"
    
    # filter dataframe
    wtrain['timestamp'] = pd.to_datetime(wtrain['timestamp'])
    start_time = pd.to_datetime("2016-{0}-{1} 00:00:00"\
                                .format(str(start_month).zfill(2), str(start_day).zfill(2)))
    end_time = pd.to_datetime("2016-{0}-{1} 23:00:00"\
                              .format(str(end_month).zfill(2), str(end_day).zfill(2)))
    df = wtrain[(wtrain.site_id == site_id) &
                (wtrain.timestamp >= start_time) &
                (wtrain.timestamp <= end_time)].reset_index(drop=True)
    nrows = df.shape[0]
    assert nrows > 0, "Invalid filter parameters"
    # plot
    fig, axes = plt.subplots(4, 2, figsize=(15, 20))
    
    # construct title and word description
    title_strs = ['Features Plots of Weather Training Set',
                  'site_id = {0}'.format(site_id),
                  'time_range = {0} ~ {1}\n'.format(start_time, end_time),
                  'x-axis represents timestamp:']
    for p in range(20, 100, 20):
        cur_idx = int(np.percentile(df.index, p))
        cur_idx_str = str(cur_idx).zfill(len(str(nrows)))
        cur_time = df.timestamp.iloc[cur_idx]
        title_strs.append("P{0}: x = {1} -> {2}".format(p, cur_idx_str, cur_time))                       
    title = '\n'.join(title_strs)
    fig.suptitle(title, size='x-large')
    # fill subplots                     
    cols_to_plot = df.columns[2:]
    for i, col in enumerate(cols_to_plot):
        cur_ax = axes[i//2][i%2]
        df[col].hist(ax=cur_ax,bins=20)
        cur_ax.set_title(col)
        

        
        