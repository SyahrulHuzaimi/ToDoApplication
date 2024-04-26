import {
  CanActivateFn,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const userRoutes: string[] = ['/dashboard', '/change'];
  const adminRoutes: string[] = ["/admin"];

  const loggedIn = authService.getLoggedInValue();
  const isAdmin = authService.getAdminValue();

  console.log("loggedInValue: " + loggedIn);

  if(adminRoutes.includes(state.url) && !isAdmin){
    return router.navigateByUrl('/');
  }
  if(userRoutes.includes(state.url) && !loggedIn){
    return router.navigateByUrl('/');
  }
  
  return true;
};
