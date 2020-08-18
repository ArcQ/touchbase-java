const modifyKeys = (ds, modifier) => {
  if (!ds) return undefined;
  let newDs;
  if (Array.isArray(ds)) {
    newDs = [];
    newDs = ds.map(v => (typeof v === 'object' ? modifyKeys(v, modifier) : v));
  } else {
    newDs = {};
    Object.entries(ds).forEach(([k, v]) => {
      newDs[modifier(k)] =
        typeof v === 'object' ? modifyKeys(ds[k], modifier) : v;
    });
  }
  return newDs;
};

export default modifyKeys;
