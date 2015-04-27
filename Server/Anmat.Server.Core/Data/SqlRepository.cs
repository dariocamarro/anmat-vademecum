﻿using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Linq.Expressions;

namespace Anmat.Server.Core.Data
{
	public class SqlRepository<T> : IRepository<T>
		where T : DataEntity
	{
		private readonly DbContext dataContext;

		public SqlRepository (DbContext dataContext, AnmatConfiguration configuration, IInitializer<T> initializer = null)
		{
			this.dataContext = dataContext;

			if (initializer != null) {
				initializer.Initialize (this, configuration.FullInitialize);
			}
		}

		public IEnumerable<T> GetAll (Expression<Func<T, bool>> predicate = null)
		{
			if (predicate == null) {
				return this.dataContext.Set<T>().AsNoTracking().AsEnumerable ();
			}

			return this.dataContext.Set<T>().Where (predicate).AsNoTracking().AsEnumerable();
		}

		public T Get (Expression<Func<T, bool>> predicate = null)
		{
			if (predicate == null) {
				return this.dataContext.Set<T>().AsNoTracking().FirstOrDefault ();
			}

			return this.dataContext.Set<T>().AsNoTracking().FirstOrDefault (predicate);
		}

		public bool Exist (Expression<Func<T, bool>> predicate = null)
		{
			return this.Get (predicate) != default (T);
		}

		public void Create (T dataEntity)
		{
			this.dataContext.Set<T>().Add (dataEntity);
			this.Save ();
		}

		public void Update (T dataEntity)
		{
			this.dataContext.Entry (dataEntity).State = EntityState.Modified;
			this.Save ();
		}

		public void Delete (T dataEntity)
		{
			this.dataContext.Set<T>().Remove (dataEntity);
			this.Save ();
		}

		public void DeleteAll()
        {
			var all = this.GetAll ();

			foreach (var entity in all) {
				this.Delete (entity);
				this.Save ();
			}
        }

		private void Save ()
		{
			this.dataContext.SaveChanges ();
		}
	}
}