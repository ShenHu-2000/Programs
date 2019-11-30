import numpy as np
import pandas as pd
%matplotlib inline
import matplotlib.pyplot as plt
import Orange
import Methods as mt
import seaborn as sns
from sklearn.model_selection import StratifiedShuffleSplit
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import chi2
from sklearn.ensemble import ExtraTreesRegressor
from sklearn.ensemble import RandomForestRegressor

meta = pd.read_csv('data/building_metadata.csv')
df_train = pd.read_csv('data/train.csv', parse_dates=['timestamp'])
df_test = pd.read_csv('data/test.csv')
weather_train = pd.read_csv('data/weather_train.csv', parse_dates=['timestamp'])
weather_test = pd.read_csv('data/weather_test.csv')


# function for reducing df size
def reduce_mem_usage(df, verbose=True):
    numerics = ['int16', 'int32', 'int64', 'float16', 'float32', 'float64']
    start_mem = df.memory_usage().sum() / 1024**2    
    for col in df.columns:
        col_type = df[col].dtypes
        if col_type in numerics:
            c_min = df[col].min()
            c_max = df[col].max()
            if str(col_type)[:3] == 'int':
                if c_min > np.iinfo(np.int8).min and c_max < np.iinfo(np.int8).max:
                    df[col] = df[col].astype(np.int8)
                elif c_min > np.iinfo(np.int16).min and c_max < np.iinfo(np.int16).max:
                    df[col] = df[col].astype(np.int16)
                elif c_min > np.iinfo(np.int32).min and c_max < np.iinfo(np.int32).max:
                    df[col] = df[col].astype(np.int32)
                elif c_min > np.iinfo(np.int64).min and c_max < np.iinfo(np.int64).max:
                    df[col] = df[col].astype(np.int64)  
            else:
                if c_min > np.finfo(np.float16).min and c_max < np.finfo(np.float16).max:
                    df[col] = df[col].astype(np.float16)
                elif c_min > np.finfo(np.float32).min and c_max < np.finfo(np.float32).max:
                    df[col] = df[col].astype(np.float32)
                else:
                    df[col] = df[col].astype(np.float64)    
    end_mem = df.memory_usage().sum() / 1024**2
    if verbose: print('Mem. usage decreased to {:5.2f} Mb ({:.1f}% reduction)'.format(end_mem, 100 * (start_mem - end_mem) / start_mem))
    return df


# merge all three training dataset
train_df = df_train.copy()
train_df = train_df.merge(meta, on="building_id", how="left")
train_df = train_df.merge(weather_train, on=['site_id', 'timestamp'], how="left")
train_df.head()

# Reducing memory
df_train = reduce_mem_usage(df_train)
df_test = reduce_mem_usage(df_test)
train_df = reduce_mem_usage(train_df)
weather_train = reduce_mem_usage(weather_train)
weather_test = reduce_mem_usage(weather_test)
meta = reduce_mem_usage(meta)

split = StratifiedShuffleSplit(n_splits = 1, test_size = 0.99)
for train_index, test_index in split.split(train_df, train_df['timestamp']):
    small_train = train_df.loc[train_index]
    test_train = train_df.loc[test_index]
display(small_train.head())
display(test_train.head())


'''Fill year_built:  We can see that year_built in some sites are completely empty. 1.For sites with single modes, We fill nans in year_built with modes.2.For site7, we use the mean of built_year of all building in this site to fill nans.3.For other sites, we use the mean of the entire column to fill nans.'''

ser = train_df.groupby('site_id')['year_built'].agg(pd.Series.mode)
dict = ser.to_dict()
print(dict)

mean_7 = train_df.loc[train_df['site_id'] == 7]['year_built'].mean()
print(mean_7)

mean_all = train_df['year_built'].mean()
print(mean_all)

dict[7] = 1961
dict.update(dict.fromkeys([6, 8, 9, 10, 11, 12, 13, 14], 1968))
print(dict)

train_df['year_built'] = train_df.apply(mt.fill_year_built, axis = 1, args = (dict, )) 
train_df.head()




'''Change floor_count and year_built. 
Fill nans in floor count with modes in each site.1.For sites that have mean value, fill nans with mean.2.For sites with no mean values, fill nans with mean of the entire dataframe.
'''

ser = train_df.groupby('site_id')['floor_count'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['floor_count'].mean()
print(total_mean)
total_mean = round(total_mean)

dict.update(dict.fromkeys([0, 2, 3, 6, 9, 11, 13, 14, 15], total_mean))
print(dict)
for i in dict:
    dict[i] = round(dict[i])
print(dict)
    
train_df['floor_count'] = train_df.apply(mt.fill_floor_count, axis = 1, args = (dict, ))
print(np.mean(train_df.isnull()))





'''Fill other na with means of the values of a feature in the same site_id or means of all values of this feature.'''
ser = train_df.groupby('site_id')['air_temperature'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['air_temperature'].mean()
print(total_mean)

for i in dict:
    dict[i] = round(dict[i],1)
print(dict)

train_df['air_temperature'] = train_df.apply(mt.fill_air_temperature, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['cloud_coverage'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['cloud_coverage'].mean()
print(total_mean)

dict.update(dict.fromkeys([7, 11], total_mean))
print(dict)

for i in dict:
    dict[i] = round(dict[i])
print(dict)

train_df['cloud_coverage'] = train_df.apply(mt.fill_cloud_coverage, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['dew_temperature'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['dew_temperature'].mean()
print(total_mean)

for i in dict:
    dict[i] = round(dict[i])
print(dict)

train_df['dew_temperature'] = train_df.apply(mt.fill_dew_temperature, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['precip_depth_1_hr'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['precip_depth_1_hr'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i])
print(dict)

train_df['precip_depth_1_hr'] = train_df.apply(mt.fill_precip_depth_1_hr, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['sea_level_pressure'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['sea_level_pressure'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i],1)
print(dict)

train_df['sea_level_pressure'] = train_df.apply(mt.fill_sea_level_pressure, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['wind_speed'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['wind_speed'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i],1)
print(dict)

train_df['wind_speed'] = train_df.apply(mt.fill_wind_speed, axis = 1, args = (dict, ))





primary_use_conversion = {}
for i, pu in enumerate(meta.primary_use.value_counts().index):
    primary_use_conversion[pu] = i
print(primary_use_conversion)

train_df['primary_use'] = train_df.primary_use.apply(lambda pu: primary_use_conversion[pu])
print(train_df)

ser = train_df.groupby('site_id')['primary_use'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['primary_use'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i])
print(dict)

train_df['primary_use'] = train_df.apply(mt.fill_primary_use, axis = 1, args = (dict, ))




train_df['wind_direction'] = train_df['wind_direction'].apply(Orange.wind_direction_conversion)

ser = train_df.groupby('site_id')['wind_direction'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['wind_direction'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i])
print(dict)

train_df['wind_direction'] = train_df.apply(mt.fill_wind_direction, axis = 1, args = (dict, ))


print(train_df.isnull().sum())






corr_matrix = train_df.corr()
corr_matrix['meter_reading'].sort_values(ascending = False)

model_1 = ExtraTreesRegressor(n_jobs=-1)
model_1.fit(train_df[['meter','primary_use','square_feet','year_built','floor_count','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed']],train_df['meter_reading'])
print(model_1.feature_importances_)

model_2 = RandomForestRegressor(n_jobs=-1)
model_2.fit(train_df[['meter','primary_use','square_feet','year_built','floor_count','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed']],train_df['meter_reading'])
print(model_2.feature_importances_)
















'''Fill year_built:  We can see that year_built in some sites are completely empty. 1.For sites with single modes, We fill nans in year_built with modes.2.For site7, we use the mean of built_year of all building in this site to fill nans.3.For other sites, we use the mean of the entire column to fill nans.'''

ser = train_df.groupby('site_id')['year_built'].agg(pd.Series.mode)
dict = ser.to_dict()
print(dict)

mean_7 = train_df.loc[train_df['site_id'] == 7]['year_built'].mean()
print(mean_7)

mean_all = train_df['year_built'].mean()
print(mean_all)

dict[7] = 1961
dict.update(dict.fromkeys([6, 8, 9, 10, 11, 12, 13, 14], 1968))
print(dict)

train_df['year_built'] = train_df.apply(mt.fill_year_built, axis = 1, args = (dict, )) 
train_df.head()




'''Change floor_count and year_built. 
Fill nans in floor count with modes in each site.1.For sites that have mean value, fill nans with mean.2.For sites with no mean values, fill nans with mean of the entire dataframe.
'''

ser = train_df.groupby('site_id')['floor_count'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['floor_count'].mean()
print(total_mean)
total_mean = round(total_mean)

dict.update(dict.fromkeys([0, 2, 3, 6, 9, 11, 13, 14, 15], total_mean))
print(dict)
for i in dict:
    dict[i] = round(dict[i])
print(dict)
    
train_df['floor_count'] = train_df.apply(mt.fill_floor_count, axis = 1, args = (dict, ))
print(np.mean(train_df.isnull()))





'''Fill other na with means of the values of a feature in the same site_id or means of all values of this feature.'''
ser = train_df.groupby('site_id')['air_temperature'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['air_temperature'].mean()
print(total_mean)

for i in dict:
    dict[i] = round(dict[i],1)
print(dict)

train_df['air_temperature'] = train_df.apply(mt.fill_air_temperature, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['cloud_coverage'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['cloud_coverage'].mean()
print(total_mean)

dict.update(dict.fromkeys([7, 11], total_mean))
print(dict)

for i in dict:
    dict[i] = round(dict[i])
print(dict)

train_df['cloud_coverage'] = train_df.apply(mt.fill_cloud_coverage, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['dew_temperature'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['dew_temperature'].mean()
print(total_mean)

for i in dict:
    dict[i] = round(dict[i])
print(dict)

train_df['dew_temperature'] = train_df.apply(mt.fill_dew_temperature, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['precip_depth_1_hr'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['precip_depth_1_hr'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i])
print(dict)

train_df['precip_depth_1_hr'] = train_df.apply(mt.fill_precip_depth_1_hr, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['sea_level_pressure'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['sea_level_pressure'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i],1)
print(dict)

train_df['sea_level_pressure'] = train_df.apply(mt.fill_sea_level_pressure, axis = 1, args = (dict, ))





ser = train_df.groupby('site_id')['wind_speed'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['wind_speed'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i],1)
print(dict)

train_df['wind_speed'] = train_df.apply(mt.fill_wind_speed, axis = 1, args = (dict, ))





primary_use_conversion = {}
for i, pu in enumerate(meta.primary_use.value_counts().index):
    primary_use_conversion[pu] = i
print(primary_use_conversion)

train_df['primary_use'] = train_df.primary_use.apply(lambda pu: primary_use_conversion[pu])
print(train_df)

ser = train_df.groupby('site_id')['primary_use'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['primary_use'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i])
print(dict)

train_df['primary_use'] = train_df.apply(mt.fill_primary_use, axis = 1, args = (dict, ))




train_df['wind_direction'] = train_df['wind_direction'].apply(Orange.wind_direction_conversion)

ser = train_df.groupby('site_id')['wind_direction'].mean()
dict = ser.to_dict()
print(dict)

total_mean = train_df['wind_direction'].mean()
print(total_mean)

for i in dict:
    if np.isnan(dict[i]):
        dict[i]=total_mean
    dict[i] = round(dict[i])
print(dict)

train_df['wind_direction'] = train_df.apply(mt.fill_wind_direction, axis = 1, args = (dict, ))


print(train_df.isnull().sum())



# Reducing memory
train_df = reduce_mem_usage(train_df)
df_test = reduce_mem_usage(df_test)

weather_train = reduce_mem_usage(weather_train)
weather_test = reduce_mem_usage(weather_test)
meta = reduce_mem_usage(meta)






corr_matrix = train_df.corr()
corr_matrix['meter_reading'].sort_values(ascending = False)

model_1 = ExtraTreesRegressor(n_jobs=-1)
model_1.fit(train_df[['meter','primary_use','square_feet','year_built','floor_count','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed']],train_df['meter_reading'])
print(model_1.feature_importances_)

model_2 = RandomForestRegressor(n_jobs=-1)
model_2.fit(train_df[['meter','primary_use','square_feet','year_built','floor_count','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed']],train_df['meter_reading'])
print(model_2.feature_importances_)
