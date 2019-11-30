import pandas as pd
import numpy as np
from scipy.stats import ks_2samp

def check_dependency(df, ref_col, other_col):
    """
    This method takes in a dataframe and two column names. Then it runs
    a permutation test and returns a p value on whether the two columns are
    dependent.
    """
    #observed value
    gpA = df.loc[df[ref_col].isnull(), other_col]
    gpB = df.loc[df[ref_col].notnull(), other_col]
    obs = ks_2samp(gpA, gpB).statistic

    #permutation
    copy = df.copy()
    perm_results = []
    for i in range(100):
        copy[ref_col] = df[ref_col].sample(frac = 1, replace = False).reset_index(drop = True)
        gpA = copy.loc[copy[ref_col].isnull(), other_col]
        gpB = copy.loc[copy[ref_col].notnull(), other_col]
        perm_results.append(ks_2samp(gpA, gpB).statistic)
    pval = np.mean(np.array(perm_results) >= obs)
    return pval

def fill_year_built(row, dict):
    """
    This method fill nans in year_built.
    """
    if np.isnan(row.loc['year_built']):
        return dict[row.loc['site_id']]
    return row.loc['year_built']

def fill_floor_count(row, dict):
    """
    This method fill nans in floor_count.
    """
    if np.isnan(row.loc['floor_count']):
        return dict[row.loc['site_id']]
    return row.loc['floor_count']


def fill_air_temperature(row, dict):
    """
    This method fill nans in air_temperature.
    """
    if np.isnan(row.loc['air_temperature']):
        return dict[row.loc['site_id']]
    return row.loc['air_temperature']


def fill_cloud_coverage(row, dict):
    """
    This method fill nans in cloud_coverage.
    """
    if np.isnan(row.loc['cloud_coverage']):
        return dict[row.loc['site_id']]
    return row.loc['cloud_coverage']


def fill_dew_temperature(row, dict):
    """
    This method fill nans in dew_temperature.
    """
    if np.isnan(row.loc['dew_temperature']):
        return dict[row.loc['site_id']]
    return row.loc['dew_temperature']


def fill_precip_depth_1_hr(row, dict):
    """
    This method fill nans in precip_depth_1_hr.
    """
    if np.isnan(row.loc['precip_depth_1_hr']):
        return dict[row.loc['site_id']]
    return row.loc['precip_depth_1_hr']

def fill_sea_level_pressure(row, dict):
    """
    This method fill nans in sea_level_pressure.
    """
    if np.isnan(row.loc['sea_level_pressure']):
        return dict[row.loc['site_id']]
    return row.loc['sea_level_pressure']


def fill_wind_speed(row, dict):
    """
    This method fill nans in wind_speed.
    """
    if np.isnan(row.loc['wind_speed']):
        return dict[row.loc['site_id']]
    return row.loc['wind_speed']


def fill_primary_use(row, dict):
    """
    This method fill nans in primary_use.
    """
    if np.isnan(row.loc['primary_use']):
        return dict[row.loc['site_id']]
    return row.loc['primary_use']

def fill_wind_direction(row, dict):
    """
    This method fill nans in wind_direction.
    """
    if np.isnan(row.loc['wind_direction']):
        return dict[row.loc['site_id']]
    return row.loc['wind_direction']


        