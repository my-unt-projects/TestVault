#!/usr/bin/env python
# coding: utf-8

# In[80]:


import matplotlib
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import datetime as dt


# In[81]:


df = pd.DataFrame({'item': ['Deliverable 1: Proposal','Deliverable 2: Requirements', 'Deliverable 3: Phase 1, Boilerplate', 'Deliverable 4: Phase 2', 'Deliverable 5: Phase 3, Final Product'],
                  
                  'start': pd.to_datetime(['today', 'today', 'today', '17 March 2025', '07 April 2025']),
                  
                 'end': pd.to_datetime(['02 Feb 2025', '24 Feb 2025', '17 March 2025', '07 April 2025', '28 April 2025'])})
print(df)


# In[82]:


df['task_duration'] = (df['end'] - df['start']).dt.days + 1 # to include also the end date


# In[83]:


print(df)


# In[84]:


df['days_to_start'] = (df['start'] - df['start'].min()).dt.days


# In[85]:


print(df)


# In[86]:


plt.barh(y=df['item'], width=df['task_duration'], left=df['days_to_start'])
plt.show()


# In[ ]:




