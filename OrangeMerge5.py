import numpy as np
import pandas as pd
%matplotlib inline
import matplotlib.pyplot as plt

# reading all csv files
meta = pd.read_csv('data/building_metadata.csv')
df_train = pd.read_csv('data/train.csv')
df_test = pd.read_csv('data/test.csv')
weather_train = pd.read_csv('data/weather_train.csv')
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

display(df_train.shape)
df_train.head()

display(meta.shape)
meta.head()

display(weather_train.shape)
weather_train.head()

 # the percentage of missing value in each column
print(weather_train.isnull().sum() / df_train.shape[0])

import datetime

def expand_dates(weather_df):          
    # Add new Features for more specific time
    # This will help us fill the missing data with forward filling method with more precise datetime
    weather_df["datetime"] = pd.to_datetime(weather_df["timestamp"])
    weather_df["day"] = weather_df["datetime"].dt.day
    weather_df["month"] = weather_df["datetime"].dt.month
    
    # Reset Index for Fast Update
    weather_df = weather_df.set_index(['site_id','day','month'])
    return weather_df



# Since most missing values occur in weather_df, we can just fill missing values in weather_df


def fill_missing_cols(weather_df):
    '''
    Steps for filling missing values in those columns that contains large proportion of missing values:
    1. Since the weather is indexed by site_id, day, month, we can groupby these indices 
        and find means for each group.
    2. Then, use forward filling method to fill all the missing values for each group. The intuition
        is that two consecutive days have similar weather.
    3. Finally, overwrite the original column with the new filled value.
    '''

    # 1. Fill cloud_coverage
    cloud_coverage_filler = weather_df.groupby(['site_id', 'day', 'month'
                                                ])['cloud_coverage'].mean()
    cloud_coverage_filler = pd.DataFrame(
        cloud_coverage_filler.fillna(method='ffill'),
        columns=["cloud_coverage"])
    weather_df.update(cloud_coverage_filler, overwrite=False)

    # 2. Fill sea_level_pressure
    sea_level_filler = weather_df.groupby(['site_id', 'day', 'month'
                                           ])['sea_level_pressure'].mean()
    sea_level_filler = pd.DataFrame(sea_level_filler.fillna(method='ffill'),
                                    columns=['sea_level_pressure'])
    weather_df.update(sea_level_filler, overwrite=False)

    # 3. Fill precip_depth_1_hr
    precip_depth_filler = weather_df.groupby(['site_id', 'day', 'month'
                                              ])['precip_depth_1_hr'].mean()
    precip_depth_filler = pd.DataFrame(
        precip_depth_filler.fillna(method='ffill'),
        columns=['precip_depth_1_hr'])
    weather_df.update(precip_depth_filler, overwrite=False)

    
    
    '''
    Steps for filling missing values in those columns that contains small proportion of missing values:
    1. Fill missing value in each group with the mean in that day, because since there are 
        small proportion of missing values, it won't affect the result that much
    2. Overwrite the original column with the new filled value.
    '''
    # 1. Fill air_temperature
    air_temperature_filler = pd.DataFrame(weather_df.groupby(
        ['site_id', 'day', 'month'])['air_temperature'].mean(),
                                          columns=["air_temperature"])
    weather_df.update(air_temperature_filler, overwrite=False)

    # 2. Fill dew_temperature
    due_temperature_filler = pd.DataFrame(weather_df.groupby(
        ['site_id', 'day', 'month'])['dew_temperature'].mean(),
                                          columns=["dew_temperature"])
    weather_df.update(due_temperature_filler, overwrite=False)

    # 3. Fill wind_direction
    wind_direction_filler = pd.DataFrame(weather_df.groupby(
        ['site_id', 'day', 'month'])['wind_direction'].mean(),
                                         columns=['wind_direction'])
    weather_df.update(wind_direction_filler, overwrite=False)

    # 4. Fill wind_speed
    wind_speed_filler = pd.DataFrame(weather_df.groupby(
        ['site_id', 'day', 'month'])['wind_speed'].mean(),
                                     columns=['wind_speed'])
    weather_df.update(wind_speed_filler, overwrite=False)
    
    # reset all indices
    weather_df = weather_df.reset_index()
    weather_df = weather_df.drop(['datetime', 'day','month'],axis=1)
    return weather_df



 # Fill weather_train
weather_df = weather_train.copy()
weather_df = expand_dates(weather_df)
weather_train = fill_missing_cols(weather_df)

weather_train = reduce_mem_usage(weather_train)
df_train = reduce_mem_usage(df_train)
meta = reduce_mem_usage(meta)


train_df = df_train.merge(meta,
                          left_on='building_id',
                          right_on='building_id',
                          how='left')
train_df = train_df.merge(weather_train,
                          how='left',
                          left_on=['site_id', 'timestamp'],
                          right_on=['site_id', 'timestamp'])

display(train_df.head())
train_df = train_df.dropna(axis=0)
print(train_df.isnull().sum()/train_df.shape[0])


from sklearn.preprocessing import LabelEncoder

def feature_engineering(df):
    # Sort by timestamp
    df.sort_values("timestamp")
    df.reset_index(drop=True)
    
    # Add more features
    df["timestamp"] = pd.to_datetime(df["timestamp"],format="%Y-%m-%d %H:%M:%S")
    df["hour"] = df["timestamp"].dt.hour
    df["weekend"] = df["timestamp"].dt.weekday
    df['square_feet'] =  np.log1p(df['square_feet'])
    
    # Remove unnecessary Columns
    drop = ["timestamp","year_built","floor_count"]
    df = df.drop(drop, axis=1)
    
    # Encode Categorical Data
    le = LabelEncoder()
    df["primary_use"] = le.fit_transform(df["primary_use"])
    return df


train_df_featengi = feature_engineering(train_df)

train_df_featengi.head()

from sklearn.ensemble import ExtraTreesRegressor
from sklearn.ensemble import RandomForestRegressor

corr_matrix = train_df_featengi.corr()
print(corr_matrix['meter_reading'].sort_values(ascending = False))
    
#Use RandomForestRegressor to determine the feature importance
model_2 = RandomForestRegressor(n_jobs=-1,n_estimators = 10)
model_2.fit(train_df_featengi[['building_id','meter','site_id','primary_use','square_feet','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed','hour','weekend']],train_df_featengi['meter_reading'])
print(model_2.feature_importances_)
Xcolumns =['building_id','meter','site_id','primary_use','square_feet','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed','hour','weekend']
dictforX = {Xcolumns[i]:model_2.feature_importances_[i] for i in range(14)}
print(dictforX)
series = pd.Series(dictforX)
print(series.sort_values(ascending=False))
series.sort_values(ascending=False).plot(kind='barh')
print(model_2.score(train_df_featengi[['building_id','meter','site_id','primary_use','square_feet','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed','hour','weekend']],train_df_featengi['meter_reading']))

# Training using sklearn.model_selection.StratifiedShuffleSplit

from sklearn.model_selection import StratifiedShuffleSplit

split = StratifiedShuffleSplit(n_splits = 1, test_size = 0.8)
for train_index, test_index in split.split(train_df_featengi, train_df_featengi['hour']):
    small_train = train_df_featengi.iloc[train_index]
    test_train = train_df_featengi.iloc[test_index]
display(small_train.head())
display(test_train.head())

model_4 = RandomForestRegressor(n_jobs=-1,n_estimators = 10)
model_4.fit(test_train[['building_id','meter','site_id','primary_use','square_feet','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed','hour','weekend']],test_train['meter_reading'])
model_4.score(small_train[['building_id','meter','site_id','primary_use','square_feet','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed','hour','weekend']],small_train['meter_reading'])







model_1 = ExtraTreesRegressor(n_jobs=-1,n_estimators = 10)
model_1.fit(train_df_featengi[['building_id','meter','site_id','primary_use','square_feet','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed','hour','weekend']],train_df_featengi['meter_reading'])
print(model_1.feature_importances_)





# drop row_id
row_ids = df_test["row_id"]
df_test.drop("row_id", axis=1, inplace=True)
df_test = reduce_mem_usage(df_test)


# fill missing values for test data
weather_df_test = weather_test.copy()
weather_df_test = expand_dates(weather_df_test)
weather_df_test = fill_missing_cols(weather_df_test)
weather_df_test = reduce_mem_usage(weather_df_test)

# merge test data
test_df = df_test.merge(meta,left_on='building_id',right_on='building_id',how='left')
test_df = test_df.merge(weather_df_test,how='left',on=['timestamp','site_id'])
print(test_df.isnull().sum()/test_df.shape[0])
train_df = train_df.dropna(axis=0)

test_df = feature_engineering(test_df)
print(test_df.isnull().sum()/test_df.shape[0])




# Determine which depth is most suitable for the trees in RandomForestRegressor
error = {}
for i in range(1,31):
    X = train_df_featengi[['building_id','meter','site_id','primary_use','square_feet','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed','hour','weekend']] 
    y = train_df_featengi['meter_reading'] 
    model_3 = RandomForestRegressor(n_jobs=-1,n_estimators = 10,max_depth = i,bootstrap=True,oob_score = True)
    model_3.fit(X,y)
    error[i] = model_3.oob_score

series_of_error = pd.Series(error)
print(series.sort_values(ascending=False))


# Determine which combination of hyperparameters is the best for RandomForestRegressor

from sklearn.model_selection import GridSearchCV

number_of_estimators = []
for i in range(10,31,5):
    number_of_estimators.append(i)
max_depth = []
for i in range(10,31,5):
    max_depth.append(i)

param_grid = [{'n_estimators':number_of_estimators,'max_depth':max_depth,'bootstrap':[True]}]
forest_regressor = RandomForestRegressor()
grid_search = GridSearchCV(forest_regressor,param_grid,cv = 5,scoring = 'neg_mean_squared_error',return_train_score=True,refit=True)
grid_search.fit(train_df_featengi[['building_id','meter','site_id','primary_use','square_feet','air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed','hour','weekend']],train_df_featengi['meter_reading'])

print(grid_search.best_estimator_)



# Stacking method: each layer is an Ensemble

import numpy as np

from ..base import Ensemble
from ..combination.combiner import Combiner

from sklearn import cross_validation


class EnsembleStack(object):

    def __init__(self, mode='probs', cv=5):
        self.mode = mode
        self.layers = []
        self.cv = cv

    def add_layer(self, ensemble):
        if isinstance(ensemble, Ensemble):
            self.layers.append(ensemble)
        else:
            raise Exception('not an Ensemble object')

    def fit_layer(self, layer_idx, X, y):
        if layer_idx >= len(self.layers):
            return
        elif layer_idx == len(self.layers) - 1:
            self.layers[layer_idx].fit(X, y)
        else:
            n_classes = len(set(y)) - 1
            n_classifiers = len(self.layers[layer_idx])
            output = np.zeros((X.shape[0], n_classes * n_classifiers))
            skf = cross_validation.StratifiedKFold(y, self.cv)
            for tra, tst in skf:
                self.layers[layer_idx].fit(X[tra], y[tra])
                out = self.layers[layer_idx].output(X[tst], mode=self.mode)
                output[tst, :] = out[:, 1:, :].reshape(
                    out.shape[0], (out.shape[1] - 1) * out.shape[2])

            self.layers[layer_idx].fit(X, y)
            self.fit_layer(layer_idx + 1, output, y)

    def fit(self, X, y):
        if self.cv > 1:
            self.fit_layer(0, X, y)
        else:
            X_ = X
            for layer in self.layers:
                layer.fit(X_, y)
                out = layer.output(X_, mode=self.mode)
                X_ = out[:, 1:, :].reshape(
                    out.shape[0], (out.shape[1] - 1) * out.shape[2])

        return self

    def output(self, X):
        input_ = X

        for layer in self.layers:
            out = layer.output(input_, mode=self.mode)
            input_ = out[:, 1:, :].reshape(
                out.shape[0], (out.shape[1] - 1) * out.shape[2])

        return out



